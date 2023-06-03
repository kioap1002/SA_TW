package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.StaffModel;
import com.example.repository.StaffRepository;

@Service
public class StaffService {
	
	@Autowired
	StaffRepository staffRepository;
	public void addStaff(StaffModel staffModel){
		staffRepository.addStaff(staffModel);
	}
	public List<StaffModel> findAll() {
		// TODO Auto-generated method stub
		return staffRepository.findAll();
	}

	public List<StaffModel> findStaffByPosition(String position) {
	    return staffRepository.findStaffByPosition(position);
	}
	
	public String findPsdByPosition(String position) {
	    return staffRepository.findPsdByPosition(position);
	}
	
	public String findPsd() {
	    return staffRepository.findPsd();
	}

}