package system;
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



import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TrafficFlowDao {

    private JdbcTemplate jdbcTemplate;

    public TrafficFlowDao() {
        DataSource dataSource = getDataSource();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/your_database_name");
        dataSource.setUsername("your_username");
        dataSource.setPassword("your_password");
        return dataSource;
    }

    public void createTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS trafficFlow_ew_s (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "roadID VARCHAR(255)," +
                "Time DATETIME(3)," +
                "Emergency_Vehicle BOOLEAN," +
                "Density DOUBLE," +
                "photo LONGBLOB)");
    }

    public void insertTrafficFlow(TrafficFlow trafficFlow) {
        jdbcTemplate.update("INSERT INTO trafficFlow_ew_s (roadID, Time, Emergency_Vehicle, Density, photo) " +
                "VALUES (?, ?, ?, ?, ?)", trafficFlow.getRoadID(), trafficFlow.getTime(),
                trafficFlow.isEmergencyVehicle(), trafficFlow.getDensity(), trafficFlow.getPhoto());
    }

    public List<TrafficFlow> searchTrafficFlows() {
        return jdbcTemplate.query("SELECT * FROM trafficFlow_ew_s", new TrafficFlowMapper());
    }

    private static class TrafficFlowMapper implements RowMapper<TrafficFlow> {
        @Override
        public TrafficFlow mapRow(ResultSet rs, int rowNum) throws SQLException {
            TrafficFlow trafficFlow = new TrafficFlow();
            trafficFlow.setId(rs.getInt("id"));
            trafficFlow.setRoadID(rs.getString("roadID"));
            trafficFlow.setTime(rs.getTimestamp("Time"));
            trafficFlow.setEmergencyVehicle(rs.getBoolean("Emergency_Vehicle"));
            trafficFlow.setDensity(rs.getDouble("Density"));
            trafficFlow.setPhoto(rs.getBytes("photo"));
            return trafficFlow;
        }
    }

    public static void main(String[] args) {
        TrafficFlowDao dao = new TrafficFlowDao();
        dao.createTable();

        // Insert example traffic flow
        TrafficFlow trafficFlow = new TrafficFlow();
        trafficFlow.setRoadID("A1");
        trafficFlow.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
        trafficFlow.setEmergencyVehicle(true);
        trafficFlow.setDensity(0.8);
        trafficFlow.setPhoto(getPhotoData()); // Replace with your photo data
        dao.insertTrafficFlow(trafficFlow);

        // Search all traffic flows
        List<TrafficFlow> trafficFlows = dao.searchTrafficFlows();
        for (TrafficFlow tf : trafficFlows) {
            System.out.println(tf);
        }
    }
}

class TrafficFlow {
    private int id;
    private String roadID;
    private java.sql.Timestamp time;
    private boolean emergencyVehicle;
    private double density;
    private byte[] photo;

    // Getters and Setters

    @Override
    public String toString() {
        return "TrafficFlow{" +
                "id=" + id +
                ", roadID='" + roadID + '\'' +
                ", time=" + time +
                ", emergencyVehicle=" + emergencyVehicle +
                ", density=" + density +
                ", photo=" + photo +
                '}';
    }
}
