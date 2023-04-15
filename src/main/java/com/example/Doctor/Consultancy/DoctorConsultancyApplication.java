package com.example.Doctor.Consultancy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class DoctorConsultancyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorConsultancyApplication.class, args);
		//openHomePage();
	}
//	private static void openHomePage() {
//		try {
//			URI homepage = new URI("http://localhost:8080/DoctorForm");
//			Desktop.getDesktop().browse(homepage);
//		} catch (URISyntaxException | IOException e) {
//			e.printStackTrace();
//		}
//	}
}
