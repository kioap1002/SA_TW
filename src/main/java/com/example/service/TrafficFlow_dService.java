package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Trafficflow_d;
import com.example.repository.TrafficFlow_dRepository;

@Service
public class TrafficFlow_dService {
	
	@Autowired
	TrafficFlow_dRepository trafficFlow_dRepository;
	public void addTrafficFlow_d(Trafficflow_d trafficFlow_d){
		trafficFlow_dRepository.addTrafficFlow_d(trafficFlow_d);
	}
	//get the 30 days Density's average : double
	public double getDensity() {
	    return trafficFlow_dRepository.getDensity();
	}
}
