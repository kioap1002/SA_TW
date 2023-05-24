import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbc_test {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/DTCS_DB";
        String username = "root";
        String password = "dtcsdb";

        try {
            // 連接到資料庫
            Connection connection = DriverManager.getConnection(url, username, password);

            // 建立 SQL 語句
            String sql = "CREATE TABLE road_situation_s (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "road_id VARCHAR(50)," +
                    "date DATE," +
                    "lane_direction VARCHAR(50)," +
                    "emergency_vehicle BOOLEAN," +
                    "density DOUBLE," +
                    "vehicle_amount INT," +
                    "image BLOB" +
                    ")";

            // 建立 Statement 物件並執行 SQL 語句
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            // 關閉資源
            statement.close();
            connection.close();

            System.out.println("資料庫表已成功建立。");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
