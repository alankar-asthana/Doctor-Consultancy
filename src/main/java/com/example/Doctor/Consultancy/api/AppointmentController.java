package com.example.Doctor.Consultancy.api;
import com.example.Doctor.Consultancy.models.Appointment;
import com.example.Doctor.Consultancy.models.Doctor;
import com.example.Doctor.Consultancy.models.Patient;
import com.example.Doctor.Consultancy.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/appointments/save")
    public ResponseEntity<Object> saveAppointment(@ModelAttribute("appointment") Appointment appointment) throws UnsupportedEncodingException {
        String patientEmail = appointment.getPatientEmail();
        String doctorEmail = appointment.getDoctorEmail();
        Date appointmentDate = appointment.getAppointmentDate();
        Time appointmentTime = appointment.getAppointmentTime();

        // check if patient exists
        Optional<Patient> patient = appointmentService.getPatientByEmail(patientEmail);
        if (patient == null) {
            return ResponseEntity.badRequest().body("Patient not found");
        }

        // check if doctor exists
        Optional<Doctor> doctor = appointmentService.getDoctorByEmail(doctorEmail);
        if (doctor == null) {
            return ResponseEntity.badRequest().body("Doctor not found");
        }

        // check if appointment slot is available
        if (!appointmentService.isAppointmentSlotAvailable(doctorEmail, appointmentDate, appointmentTime)) {
            return ResponseEntity.badRequest().body("Appointment slot not available");
        }

        // create new appointment object
        Appointment newappointment = new Appointment();
        newappointment.setId(UUID.randomUUID().toString());
        newappointment.setPatientName(Patient.getName());
        newappointment.setPatientEmail(Patient.getEmail());
        newappointment.setDoctorName(Doctor.getName());
        newappointment.setDoctorEmail(Doctor.getEmail());
        newappointment.setAppointmentDate(appointmentDate);
        newappointment.setAppointmentTime(appointmentTime);
        newappointment.setVideoLink(newappointment.generateVideoCallLink());
        newappointment.setSymptoms(appointment.getSymptoms());

        // save appointment
        appointmentService.saveAppointment(newappointment);

        // set appointment slot as unavailable
        //appointmentService.setAppointmentSlotUnavailable(doctorEmail, appointmentDate, appointmentTime);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/appointments/patient/{email}")
    public List<Appointment> getAppointmentsByPatientEmail(@PathVariable String email) {
        return appointmentService.getAppointmentByPatientEmail(email);
    }

    @GetMapping("/appointments/doctor/{email}")
    public List<Appointment> getAppointmentsByDoctorEmail(@PathVariable String email) {
        return appointmentService.getAppointmentByDoctorEmail(email);
    }
    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Object> cancelAppointment(@PathVariable("id") String appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentById(appointmentId);

        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();

            // set appointment slot as available
            //appointmentService.setAppointmentSlotAvailable(appointment.getDoctorEmail(), appointment.getAppointmentDate(), appointment.getAppointmentTime());

            // delete appointment from database
            appointmentService.deleteAppointment(appointmentId);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

