package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.intersection_static;
import com.example.repository.intersection_staticRepository;

@Service
public class intersection_staticService {
	@Autowired
	intersection_staticRepository intersection_staticRepository;
	
	public void addIntersection_static(intersection_static intersection_static){
		intersection_staticRepository.addIntersection_static(intersection_static);
	}
	
	//get TotalSeconds by intersection_id : integer
	public int getTotalSecondsByRoadIntersectionId(String R_I_Id) {
	    return intersection_staticRepository.getTotalSecondsByRoadIntersectionId(R_I_Id);
	}
	
	//get laneWidth by intersection_id : integer
	public int getLaneWidthByRoadIntersectionId(String R_I_Id,String direc) {
	    return intersection_staticRepository.getLaneWidthByRoadIntersectionId(R_I_Id, direc);
	}
	
	
	//get the speedLimit by intersection_id : integer
	public int getSpeedLimit(String R_I_Id,String direc) {
	    return intersection_staticRepository.getSpeedLimit(R_I_Id, direc);
	}
	
	//get roadRight by intersection_id : boolean
	public boolean getRroadRightByRoadIntersectionId(String R_I_Id,String direc) {
		return intersection_staticRepository.getRroadRightByRoadIntersectionId(R_I_Id, direc);
	}
	
	
	//get the glt by intersection_id : integer
	public int getGreenLightTime(String R_I_Id,String direc) {
		return intersection_staticRepository.getGreenLightTime(R_I_Id, direc);
	}

}
