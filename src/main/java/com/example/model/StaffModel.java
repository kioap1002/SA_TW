package com.example.model;

import org.springframework.stereotype.Component;

import lombok.Data;


@Data
@Component
public class StaffModel {
	  private int id;
	  private String email;
	  private String phone;
	  private String password;
	  private String position;

}