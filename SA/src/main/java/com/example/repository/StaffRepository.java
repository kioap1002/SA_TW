package com.example.repository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.StaffModel;

@Repository
public class StaffRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void addStaff(StaffModel staffModel){
		System.out.println("EXCUTE INSERT MEMBER");
	  jdbcTemplate.update("INSERT INTO staffInfo(PASSWORD, EMAIL, POSITION, PHONE, CREATE_DATE) "
	  		+ "VALUES (?,?,?,?,NOW())",staffModel.getPassword(), staffModel.getEmail(),
	  		staffModel.getPosition(),staffModel.getPhone());
	}

	public List<StaffModel> findAll() {
	    String sql = "SELECT * FROM staffInfo";
	    return jdbcTemplate.query(
	        sql,
	        new RowMapper<StaffModel>() {
	            public StaffModel mapRow(ResultSet rs, int rowNum) throws SQLException {
	                StaffModel staffModel = new StaffModel();
	                staffModel.setPassword(rs.getString("password"));
	                staffModel.setEmail(rs.getString("email"));
	                staffModel.setPhone(rs.getString("phone"));
	                staffModel.setPosition(rs.getString("position"));
	                return staffModel;
	            }
	        });
	}
	
	
	//find records by position(certain value)
	public List<StaffModel> findStaffByPosition(String position) {
	    String sql = "SELECT * FROM staffInfo WHERE position = ?";
	    return jdbcTemplate.query(
	        sql,
	        new Object[]{position},
	        new RowMapper<StaffModel>() {
	            public StaffModel mapRow(ResultSet rs, int rowNum) throws SQLException {
	                StaffModel staffModel = new StaffModel();
	                staffModel.setPassword(rs.getString("password"));
	                staffModel.setEmail(rs.getString("email"));
	                staffModel.setPhone(rs.getString("phone"));
	                staffModel.setPosition(rs.getString("position"));
	                return staffModel;
	            }
	        });
	}
	
	//get the newest data by value : String
	public String findPsdByPosition(String position) {
	    String sql = "SELECT password FROM staffInfo WHERE position = ? limit 1";
	    return jdbcTemplate.queryForObject(sql, new Object[]{position}, String.class);
	}
	
	//get the newest data : String
	public String findPsd() {
	    String sql = "SELECT password FROM staffInfo ORDER BY create_date DESC limit 1";
	    return jdbcTemplate.queryForObject(sql, String.class);
	}

}