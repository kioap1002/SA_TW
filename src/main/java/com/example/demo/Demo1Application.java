package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"com.example.demo","com.example.model","com.example.controller","com.example.service","com.example.repository"})
@EntityScan({"com.example.demo","com.example.model","com.example.controller","com.example.service","com.example.repository"})
@EnableScheduling
public class Demo1Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
	}

}
