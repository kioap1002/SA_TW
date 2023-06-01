package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.TrafficFlow_ns_s;
import com.example.repository.TrafficFlow_ns_sRepository;

@Service
public class TrafficFlow_ns_sService {
	@Autowired
	TrafficFlow_ns_sRepository trafficFlow_ns_sRepository;
	
	public void addTrafficFlow_ns_s(TrafficFlow_ns_s trafficFlow_ns_s){
		trafficFlow_ns_sRepository.addTrafficFlow_ns_s(trafficFlow_ns_s);
	}
	
	//delete all rows
	public void deleteData() {
	    trafficFlow_ns_sRepository.deleteData();
	}
	
	//get the newest Density info : boolean
	public double getDensity() {
	    return trafficFlow_ns_sRepository.getDensity();
	}
	
	//get the newest EV info : boolean
	public boolean getEV() {
	    return trafficFlow_ns_sRepository.getEV();
	}

}
