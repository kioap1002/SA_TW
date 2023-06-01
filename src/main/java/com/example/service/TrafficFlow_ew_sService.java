package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.TrafficFlow_ew_s;
import com.example.repository.TrafficFlow_ew_sRepository;

@Service
public class TrafficFlow_ew_sService {
	@Autowired
	TrafficFlow_ew_sRepository trafficFlow_ew_sRepository;
	
	public void addTrafficFlow_ew_s(TrafficFlow_ew_s trafficFlow_ew_s){
		trafficFlow_ew_sRepository.addTrafficFlow_ew_s(trafficFlow_ew_s);
	}
	
	//delete all rows
	public void deleteData() {
		trafficFlow_ew_sRepository.deleteData();
	}
	
	//get the newest Density info : boolean
	public double getDensity() {
	    return trafficFlow_ew_sRepository.getDensity();
	}
	
	//get the newest EV info : boolean
	public boolean getEV() {
	    return trafficFlow_ew_sRepository.getEV();
	}
}
