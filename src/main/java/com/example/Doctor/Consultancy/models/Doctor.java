package com.example.Doctor.Consultancy.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Doctors")
public class Doctor {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String specialty;
    private String degree;
    private String licenseNumber;
    private String address;
    private String phoneNumber;



    public Doctor(String name, String email, String password, String specialty, String degree, String licenseNumber, String address, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.specialty = specialty;
        this.degree = degree;
        this.licenseNumber = licenseNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Doctor() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
