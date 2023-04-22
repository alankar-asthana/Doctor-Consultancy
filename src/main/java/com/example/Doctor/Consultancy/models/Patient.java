package com.example.Doctor.Consultancy.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "Patients")
public class Patient {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String mobileno;

    public Patient() {}
    public Patient(String name, String email, String password, String address, String mobileno) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.mobileno = mobileno;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }
}
