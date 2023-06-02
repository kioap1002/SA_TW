package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.TrafficFlow_ns_s;

@Repository
public class TrafficFlow_ns_sRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void addTrafficFlow_ns_s(TrafficFlow_ns_s trafficFlow_ns_s){
		System.out.println("EXCUTE INSERT TRAFFICFLOWEWD");
		jdbcTemplate.update("INSERT INTO TrafficFlow_ns_s(ID, ROAD_INTERSECTION_ID, TIME, EMERGENCY_VEHICLE, DENSITY, PHOTO, CREATE_DATE) "
	  		+ "VALUES (?,?,?,?,?,?,NOW())",trafficFlow_ns_s.getID(), trafficFlow_ns_s.getRoad_Intersection_ID(),
	  		trafficFlow_ns_s.getTime(),trafficFlow_ns_s.getEmergency_Vehicle(),trafficFlow_ns_s.getDensity(),trafficFlow_ns_s.getPhoto());
	}
	
	//delete all rows
	public void deleteData() {
	    String sql = "DELETE FROM TrafficFlow_ns_s";
	    jdbcTemplate.update(sql);
	}
	
	//get the newest Density info : boolean
	public double getDensity() {
	    String sql = "SELECT Density FROM TrafficFlow_ns_s ORDER BY create_date DESC limit 1";
	    return jdbcTemplate.queryForObject(sql, Double.class);
	}
	
	//get the newest EV info : boolean
	public boolean getEV() {
	    String sql = "SELECT Emergency_Vehicle FROM TrafficFlow_ns_s ORDER BY create_date DESC limit 1";
	    return jdbcTemplate.queryForObject(sql, Boolean.class);
	}
	
	//get getDensity_avg : double
	public double getDensity_avg() {
	    String sql = "SELECT AVG(Density) FROM (SELECT Density FROM TrafficFlow_ns_s) AS subquery";
	    return jdbcTemplate.queryForObject(sql, Double.class);
	}
}
