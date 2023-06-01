package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.intersection_static;

@Repository
public class intersection_staticRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void addIntersection_static(intersection_static intersection_static){
		System.out.println("EXCUTE INSERT INTERSECTION_STATIC");
		jdbcTemplate.update("INSERT INTO intersection_static(ROAD_INTERSECTION_ID, INTERSECTION_NAME, TOTALSECONDS, SPEEDLIMIT_EW, LANEWIDTH_EW, ROADRIGHT_EW, GREENLIGHTTIME_EW, SPEEDLIMIT_NS, LANEWIDTH_NS, ROADRIGHT_NS, GREENLIGHTTIME_NS, CREATE_DATE) "
	  		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,NOW())",intersection_static.getRoad_Intersection_ID(), intersection_static.getIntersection_Name(), intersection_static.getTotalSeconds(), intersection_static.getSpeedLimit_ew()
	  		, intersection_static.getLaneWidth_ew(),intersection_static.getRoadRight_ew(),intersection_static.getGreenLightTime_ew(), intersection_static.getSpeedLimit_ns(), intersection_static.getLaneWidth_ns(),
	  		intersection_static.getRoadRight_ns(),intersection_static.getGreenLightTime_ns());
	}
	
	//get TotalSeconds by intersection_id : integer
	public int getTotalSecondsByRoadIntersectionId(String R_I_Id) {
		String sql;
		sql = "SELECT TotalSeconds FROM intersection_static WHERE Road_Intersection_ID "+ R_I_Id + " = ? limit 1";
	    return jdbcTemplate.queryForObject(sql, new Object[]{R_I_Id}, Integer.class);
	}
	
	//get laneWidth by intersection_id : integer
	public int getLaneWidthByRoadIntersectionId(String R_I_Id,String direc) {
		String sql;
		if(direc == "ew") {
			sql = "SELECT LaneWidth_ew FROM intersection_static WHERE Road_Intersection_ID "+ R_I_Id + " = ? limit 1";
		}else {
			sql = "SELECT LaneWidth_ns FROM intersection_static WHERE Road_Intersection_ID "+ R_I_Id + " = ? limit 1";
		}
	    return jdbcTemplate.queryForObject(sql, new Object[]{R_I_Id}, Integer.class);
	}
	
	
	//get the speedLimit by intersection_id : integer
	public int getSpeedLimit(String R_I_Id,String direc) {
		String sql;
		if(direc == "ew") {
			sql = "SELECT SpeedLimit_ew FROM intersection_static WHERE Road_Intersection_ID "+ R_I_Id + " = ? limit 1";
		}else {
			sql = "SELECT SpeedLimit_ns FROM intersection_static WHERE Road_Intersection_ID "+ R_I_Id + " = ? limit 1";
		}
	    return jdbcTemplate.queryForObject(sql, new Object[]{R_I_Id}, Integer.class);
	}
	
	//get roadRight by intersection_id : boolean
	public boolean getRroadRightByRoadIntersectionId(String R_I_Id,String direc) {
		String sql;
		if(direc == "ew") {
			sql = "SELECT RoadRight_ew FROM intersection_static WHERE Road_Intersection_ID "+ R_I_Id + " = ? limit 1";
		}else {
			sql = "SELECT RoadRight_ns FROM intersection_static WHERE Road_Intersection_ID "+ R_I_Id + " = ? limit 1";
		}
	    return jdbcTemplate.queryForObject(sql, new Object[]{R_I_Id}, Boolean.class);
	}
	
	
	//get the glt by intersection_id : integer
	public int getGreenLightTime(String R_I_Id,String direc) {
		String sql;
		if(direc == "ew") {
			sql = "SELECT GreenLightTime_ew FROM intersection_static WHERE Road_Intersection_ID "+ R_I_Id + " = ? limit 1";
		}else {
			sql = "SELECT GreenLightTime_ns FROM intersection_static WHERE Road_Intersection_ID "+ R_I_Id + " = ? limit 1";
		}
	    return jdbcTemplate.queryForObject(sql, new Object[]{R_I_Id}, Integer.class);
	}
}
