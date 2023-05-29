import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class jdbc_test {
    private static final String URL = "jdbc:mysql://localhost:3306/DTCS_DB";
    private static final String USERNAME = "dtcs";
    private static final String PASSWORD = "dtcsdb6253";
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Connection conn = null;
        try {
            // 連接到資料庫
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // 創建資料表
            createTables(conn);
            // 插入Blob檔案
            String img_path = "path/to/your/file.jpg";
            File file = new File(img_path);
            // insertBlob(conn, "photos", "photo_id", file);
            insertData(conn, "photos", "photo_id", file, 1, 3.14);
            // 檢索並顯示Blob檔案
            retrieveBlob(conn, "photos", "photo_id");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 關閉資源
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }

    private static void createTables(Connection conn) throws SQLException {
        String createTrafficFlowData_s_ewTableQuery = "CREATE TABLE IF NOT EXISTS trafficflowdata_s_ew ("
                + "ID INT AUTO_INCREMENT PRIMARY KEY,"
                + "Road_Intersection_ID String"
                + "Time DateTime"  //?
                + "Emergency_Vehicle Boolean"
                + "Density Double"
                +")";
        String createTrafficFlowData_s_nsTableQuery = "CREATE TABLE IF NOT EXISTS trafficflowdata_s_ew ("
                + "ID INT AUTO_INCREMENT PRIMARY KEY,"
                + "Road_Intersection_ID String"
                + "Time DateTime"  //?
                + "Emergency_Vehicle Boolean"
                + "Density Double"
                +")";
        String createTrafficFlowData_dTableQuery = "CREATE TABLE IF NOT EXISTS trafficflowdata_s_ew ("
                + "ID INT AUTO_INCREMENT PRIMARY KEY,"
                + "Road_Intersection_ID String"
                + "Date Date"  //?
                + "Density_avg Double"
                +")";
        String createIntersectionInfoTableQuery = "CREATE TABLE IF NOT EXISTS trafficflowdata_s_ew ("
                + "Road_Intersection_ID String PRIMARY KEY,"
                + "Intersection_Name String"
                + "TotalSeconds INT"
                + "SpeedLimit_ew INT"
                + "LaneWidth_ew INT"
                + "RoadRight_ew BOOLEAN"  //?
                + "GreenLightTime_ew INT"
                + "SpeedLimit_ns INT"
                + "LaneWidth_ns INT"
                + "RoadRight_ns BOOLEAN"  //?
                + "GreenLightTime_ns INT"
                +")";
        // conn.createStatement().executeUpdate(createPhotosTableQuery);
        conn.createStatement().executeUpdate(createTrafficFlowData_s_ewTableQuery);
        conn.createStatement().executeUpdate(createTrafficFlowData_s_nsTableQuery);
        conn.createStatement().executeUpdate(createTrafficFlowData_dTableQuery);
        conn.createStatement().executeUpdate(createIntersectionInfoTableQuery);
        // 創建其他資料表
        
    }

    private static void insertData(Connection conn, String tableName, String columnName, File file, int intValue, double doubleValue) throws SQLException, FileNotFoundException, IOException {
        String insertQuery = "INSERT INTO " + tableName + " (" + columnName + ", data_int, data_double) VALUES (?, ?, ?)";  //=>data_int, data_double : 欄位名稱
        PreparedStatement pstmt = conn.prepareStatement(insertQuery);
        
        try (FileInputStream fis = new FileInputStream(file)) {
            pstmt.setBinaryStream(1, fis, (int) file.length());
            pstmt.setInt(2, intValue);
            pstmt.setDouble(3, doubleValue);
            pstmt.executeUpdate();
        }
    }
    // private static void createTables(Connection conn) throws SQLException {
    //     // Create the 'photos' table
    //     String createPhotosTableQuery = "CREATE TABLE IF NOT EXISTS photos ("
    //             + "photo_id INT AUTO_INCREMENT PRIMARY KEY,"
    //             + "photo BLOB,"
    //             + "data_int INT,"
    //             + "data_double DOUBLE"
    //             + ")";
    //     conn.createStatement().executeUpdate(createPhotosTableQuery);

    //     // Create other tables
    //     // ...
    // }
    private static void retrieveBlob(Connection conn, String tableName, String columnName) throws SQLException {
        String selectQuery = "SELECT " + columnName + " FROM " + tableName;
        ResultSet rs = conn.createStatement().executeQuery(selectQuery);

        if (rs.next()) {
            byte[] blobData = rs.getBytes(columnName);
            // 執行你想要的操作，例如將Blob檔案寫入磁碟或顯示圖片等
        }

        rs.close();
    }

    //找路權
    private static boolean retrieveRoadRight(Connection conn, String tableName, String columnA, String columnC, String valueA) throws SQLException {
        String selectQuery = "SELECT " + columnC + " FROM " + tableName + " WHERE " + columnA + " = ? LIMIT 1";
        PreparedStatement pstmt = conn.prepareStatement(selectQuery);
        pstmt.setString(1, valueA);
        //value = intersectionID
        ResultSet rs = pstmt.executeQuery();
        int result = 0;//false:0=ew, true:1=ns
        if (rs.next()) {
            result = rs.getInt(columnC);
        }
        rs.close();
        pstmt.close();
        return result==0?false:true;

        /*透過A欄位找到C欄位的值
         * 在這個修改後的方法中，我們使用了 PreparedStatement 來執行帶有參數的 SQL 查詢。
         * 透過將欄位 A 的值作為參數傳遞，我們可以通過 WHERE 子句來檢索符合條件的資料。
         * 請注意，我們新增了 columnA、columnC 和 valueA 三個參數，分別用於指定欄位 A 的名稱、欄位 C 的名稱以及要匹配的欄位 A 的值。
         * 在這個範例中，我們假設欄位 C 是一個字串欄位。
         * 如果你的需求是檢索其他類型的欄位，你需要根據相應的資料類型進行調整。
         * 請在需要的地方調用 retrieveCValueByA 方法，並傳遞相應的參數。
         * 這將檢索到符合欄位 A 值的相應欄位 C 的值。你可以根據需要進一步處理這個值，例如將其打印出來或執行其他操作。
         */
    }


    //限定資料筆數=>limit=30
    private static double retrieveDensity_30_avg(Connection conn, String tableName, String columnA, String columnB) throws SQLException {
        String selectQuery = "SELECT " + columnB + " FROM " + tableName + " ORDER BY " + columnA + " DESC LIMIT 30";
        //columnB=密度，columnA=日期，tableName=日資料庫，DESC=由大到小排序
        PreparedStatement pstmt = conn.prepareStatement(selectQuery);
        // pstmt.setInt(1, limit);
        ResultSet rs = pstmt.executeQuery();
        double sum = 0;
        while (rs.next()) {
            double valueB = rs.getDouble(columnB);
            sum += valueB;
        }
        rs.close();
        pstmt.close();
        return sum;
    }
}

