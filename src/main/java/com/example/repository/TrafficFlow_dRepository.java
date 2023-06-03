package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.Trafficflow_d;

@Repository
public class TrafficFlow_dRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void addTrafficFlow_d(Trafficflow_d trafficFlow_d){
		System.out.println("EXCUTE INSERT TRAFFICFLOWD");
		jdbcTemplate.update("INSERT INTO TrafficFlow_d(ID, ROAD_INTERSECTION_ID, DATE, DENSITY_AVG, CREATE_DATE) "
	  		+ "VALUES (?,?,?,?,NOW())",trafficFlow_d.getID(), trafficFlow_d.getRoad_Intersection_ID(),
	  		trafficFlow_d.getDate(),trafficFlow_d.getDensity_avg());
	}
	//get the 30 days Density's average : boolean
	public double getDensity() {
	    String sql = "SELECT AVG(Density_avg) FROM (SELECT Density_avg FROM TrafficFlow_d ORDER BY create_date DESC LIMIT 30) AS subquery";
	    return jdbcTemplate.queryForObject(sql, Double.class);
	}
	
}
