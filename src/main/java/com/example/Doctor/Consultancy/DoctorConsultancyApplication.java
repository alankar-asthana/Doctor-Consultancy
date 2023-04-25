package com.example.Doctor.Consultancy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.Doctor.Consultancy")
@EnableMongoRepositories(basePackages = "com.example.Doctor.Consultancy.repository")
public class DoctorConsultancyApplication {
	public static void main(String[] args) {
		SpringApplication.run(DoctorConsultancyApplication.class, args);
	}
}
