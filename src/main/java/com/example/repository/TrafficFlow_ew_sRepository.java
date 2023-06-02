package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.TrafficFlow_ew_s;

@Repository
public class TrafficFlow_ew_sRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void addTrafficFlow_ew_s(TrafficFlow_ew_s trafficFlow_ew_s){
		System.out.println("EXCUTE INSERT TRAFFICFLOWEWD");
		jdbcTemplate.update("INSERT INTO TrafficFlow_ew_s(ID, ROAD_INTERSECTION_ID, TIME, EMERGENCY_VEHICLE, DENSITY, PHOTO, CREATE_DATE) "
	  		+ "VALUES (?,?,?,?,?,?,NOW())",trafficFlow_ew_s.getID(), trafficFlow_ew_s.getRoad_Intersection_ID(),
	  		trafficFlow_ew_s.getTime(),trafficFlow_ew_s.getEmergency_Vehicle(),trafficFlow_ew_s.getDensity(),trafficFlow_ew_s.getPhoto());
	}
	
	//delete all rows
	public void deleteData() {
	    String sql = "DELETE FROM TrafficFlow_ew_s";
	    jdbcTemplate.update(sql);
	}
	
	//get the newest Density info : boolean
	public double getDensity() {
	    String sql = "SELECT Density FROM TrafficFlow_ew_s ORDER BY create_date DESC limit 1";
	    return jdbcTemplate.queryForObject(sql, Double.class);
	}
	
	
	//get the newest EV info : boolean
	public boolean getEV() {
	    String sql = "SELECT Emergency_Vehicle FROM TrafficFlow_ew_s ORDER BY create_date DESC limit 1";
	    return jdbcTemplate.queryForObject(sql, Boolean.class);
	}
	
	//get getDensity_avg : double
	public double getDensity_avg() {
	    String sql = "SELECT AVG(Density) FROM (SELECT Density FROM TrafficFlow_ew_s) AS subquery";
	    return jdbcTemplate.queryForObject(sql, Double.class);
	}
	
}
