package com.example.Doctor.Consultancy.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Document(collection = "Appointments")
public class Appointment {

    @Id
    private String id;
    private String patientName;
    private String patientEmail;
    private String doctorName;
    private String doctorEmail;
    private Date appointmentDate;
    private Time appointmentTime;
    private String videoLink;
    private String symptoms;
    private AppointmentStatus appointmentStatus;

    public Appointment(String patientName, String patientEmail, String doctorName,String doctorEmail, Date appointmentDate, Time appointmentTime, String videoLinkLink, String symptoms) {
        this.patientName = patientName;
        this.patientEmail = patientEmail;
        this.doctorName = doctorName;
        this.doctorEmail = doctorEmail;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.videoLink = videoLinkLink;
        this.symptoms = symptoms;
    }

    public Appointment() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Time getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String generateVideoCallLink() throws UnsupportedEncodingException {
        // Generate a Google Meet link with a randomly generated meeting ID
        String meetingId = UUID.randomUUID().toString().substring(0, 8);
        String encodedMeetingId = URLEncoder.encode(meetingId, "UTF-8");
        String url = "https://meet.google.com/new?hs=122&pli=1&authuser=0#" + encodedMeetingId;

        return url;
    }
    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public enum AppointmentStatus {
        AVAILABLE, UNAVAILABLE
    }

}

