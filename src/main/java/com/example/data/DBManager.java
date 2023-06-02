package com.example.data;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.Intersection_static;
import com.example.model.TrafficFlow_ew_s;
import com.example.model.TrafficFlow_ns_s;
import com.example.model.Trafficflow_d;
import com.example.service.Intersection_staticService;
import com.example.service.TrafficFlow_dService;
import com.example.service.TrafficFlow_ew_sService;
import com.example.service.TrafficFlow_ns_sService;

public class DBManager {
	@Autowired
	// static data
	Intersection_staticService intersection_staticService;  
	public void addIntersection_static(Intersection_static intersection_static){
		intersection_staticService.addIntersection_static(intersection_static);
	}
	public int getTotalSecondsByRoadIntersectionId(String R_I_Id) {
	    return intersection_staticService.getTotalSecondsByRoadIntersectionId(R_I_Id);
	}
	public int getLaneWidthByRoadIntersectionId(String R_I_Id,String direc) {
	    return intersection_staticService.getLaneWidthByRoadIntersectionId(R_I_Id, direc);
	}
	public int getSpeedLimit(String R_I_Id,String direc) {
	    return intersection_staticService.getSpeedLimit(R_I_Id, direc);
	}
	public boolean getRroadRightByRoadIntersectionId(String R_I_Id,String direc) {
		return intersection_staticService.getRroadRightByRoadIntersectionId(R_I_Id, direc);
	}
	public int getGreenLightTime(String R_I_Id,String direc) {
		return intersection_staticService.getGreenLightTime(R_I_Id, direc);
	}
	
	// traffic_d data
	TrafficFlow_dService trafficFlow_dService;
	public void addTrafficFlow_d(Trafficflow_d trafficFlow_d){
		trafficFlow_dService.addTrafficFlow_d(trafficFlow_d);
	}
	public double getDensity() {  //get the 30 days Density's average : double
	    return trafficFlow_dService.getDensity();
	}
	
	// traffic_ew_ data
	TrafficFlow_ew_sService trafficFlow_ew_sService;
	public void addTrafficFlow_ew_s(TrafficFlow_ew_s trafficFlow_ew_s){
		trafficFlow_ew_sService.addTrafficFlow_ew_s(trafficFlow_ew_s);
	}
	public void deleteData_ew() {
		trafficFlow_ew_sService.deleteData();
	}
	public double getDensity_ew() {
	    return trafficFlow_ew_sService.getDensity();
	}
	public boolean getEV_ew() {
	    return trafficFlow_ew_sService.getEV();
	}
	public double getDensity_avg_ew() {
	    return trafficFlow_ew_sService.getDensity_avg();
	}
	
	// traffic_ns_ data
	TrafficFlow_ns_sService trafficFlow_ns_sService;
	public void addTrafficFlow_ns_s(TrafficFlow_ns_s trafficFlow_ns_s){
		trafficFlow_ns_sService.addTrafficFlow_ns_s(trafficFlow_ns_s);
	}
	public void deleteData_ns() {
		trafficFlow_ns_sService.deleteData();
	}
	public double getDensity_ns() {
	    return trafficFlow_ns_sService.getDensity();
	}
	public boolean getEV_ns() {
	    return trafficFlow_ns_sService.getEV();
	}
	public double getDensity_avg_ns() {
	    return trafficFlow_ns_sService.getDensity_avg();
	}
}
