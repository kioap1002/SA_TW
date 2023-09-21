package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.StaffModel;
import com.example.service.StaffService;

@RestController
public class StaffController {

	@Autowired
	StaffModel staffModel;
	
	@Autowired
	StaffService staffService;
	@RequestMapping("/add")
	public String hello(){
		staffModel = new StaffModel();
		staffModel.setPassword("98596");
		staffModel.setEmail("abcd@email.com");
		staffModel.setPhone("2594948");
		staffModel.setPosition("QAQ");
		staffService.addStaff(staffModel);
		return "New Staff added" +" "+ staffModel.getPassword();
	}

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public String doSomething() {
		// System.out.println("ToString:" + staffModel.toString());
		// System.out.println("HashCode:" + staffModel.hashCode());
		staffModel = new StaffModel();
		staffModel.setPassword("1234");
		staffModel.setEmail("email@email.com");
		staffModel.setPhone("22334455");
		staffModel.setPosition("Manager");
		staffService.addStaff(staffModel);
		return staffModel.toString();
	}
	
	@RequestMapping("/addd")
	public List<StaffModel> showStaff(StaffModel staffModel) {
		List<StaffModel> staff = staffService.findAll();
		// staffModel.addAttribute("employees", staff);
		return staff;
	}
	
	@RequestMapping("/abcd")
	public List<StaffModel> showQ(StaffModel staffModel) {
		List<StaffModel> staff = staffService.findStaffByPosition("Client");
		return staff;
	}
	
	@RequestMapping("/pig")
	public String showCbyB(StaffModel staffModel) {
		String staff = staffService.findPsdByPosition("Manager");
		return staff;
	}
	
	@RequestMapping("/psd")
	public String showPsd(StaffModel staffModel) {
		String staff = staffService.findPsd();
		return staff;
	}
}