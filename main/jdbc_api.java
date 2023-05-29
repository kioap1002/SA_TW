package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;


public class jdbc_api {


    private static void excute(boolean con, String str)throws SQLException, IOException {

        while(con){
            try {
                if
                receive(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    
    private static void receive(String sql) throws SQLException, FileNotFoundException, IOException {
        execute()
    }

    private static String insertData_ew_s(Connection conn, String intersectionID, Time time, boolean EV, double density, File file) throws SQLException, FileNotFoundException, IOException {
        return "INSERT INTO trafficflowdata_ew_s (Road_Intersection_ID, Time, Emergency_Vehicle, Density, photo) VALUES (?, ?, ?, ?, ?)";  //=>data_int, data_double : 欄位名稱
        // PreparedStatement pstmt = conn.prepareStatement(insertQuery); 
    }
    private static String insertData_ns_s(Connection conn, String intersectionID, Time time, boolean EV, double density, File file) throws SQLException, FileNotFoundException, IOException {
        return "INSERT INTO trafficflowdata_ns_s (Road_Intersection_ID, Time, Emergency_Vehicle, Density, photo) VALUES (?, ?, ?, ?, ?)";  //=>data_int, data_double : 欄位名稱
    }
    private static String insertData_d(Connection conn, String intersectionID, Date date, double density) throws SQLException, FileNotFoundException, IOException {
        return "INSERT INTO trafficflowdata_d (Road_Intersection_ID, Date, Density_avg) VALUES (?, ?, ?)";  //=>data_int, data_double : 欄位名稱
    }

}

