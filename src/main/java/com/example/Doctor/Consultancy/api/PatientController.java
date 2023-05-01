package com.example.Doctor.Consultancy.api;

import com.example.Doctor.Consultancy.models.Appointment;
import com.example.Doctor.Consultancy.models.Doctor;
import com.example.Doctor.Consultancy.models.Patient;
import com.example.Doctor.Consultancy.models.Patient;
import com.example.Doctor.Consultancy.service.AppointmentService;
import com.example.Doctor.Consultancy.service.PatientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/patient-register")
    public String showRegistrationForm(){return "PatientForm";}


    @PostMapping("/patient-register")
    public String createPatient(@ModelAttribute Patient patient, RedirectAttributes attributes) {
        System.out.println(patient);
        Patient createdPatient = patientService.createPatient(patient);
        if (createdPatient != null) {
            return "redirect:/patient-login";
        } else {
            attributes.addFlashAttribute("error", "Registration failed. Please try again.");
            return "redirect:/patient-register";
        }
    }

    @GetMapping("/patient-login")
    public String showLoginForm() {
        return "PatientLogin";
    }

    @PostMapping("/patient-login")
    public String login(@ModelAttribute("patient") Patient patient, RedirectAttributes attributes, HttpSession session) {
        Patient authenticatedPatient = patientService.authenticate(patient.getEmail(), patient.getPassword());

        if (authenticatedPatient == null) {
            return "redirect:/patient-register";
        } else {
            session.setAttribute("patientEmail", authenticatedPatient.getEmail());
            return "redirect:/patient-dashboard?email=" + authenticatedPatient.getEmail();
        }
    }
    @PostMapping("/patient-logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home-page";
    }
    @GetMapping("/patient-dashboard")
    public String showDashboard() {
        return "PatientDashboard";
    }

//    @GetMapping("/patient-details/{email}")
//    public String getPatientDetails(@PathVariable String email, Model model) {
//        // Retrieve patient data from API
//        Patient patient = patientService.getPatientByEmail(email);
//        model.addAttribute("patient", patient);
//
//        // Retrieve appointment data from API
//        List<Appointment> appointments = appointmentService.getAppointmentByPatientEmail(email);
//        model.addAttribute("appointments", appointments);
//
//        // Render the patient-details.html template
//        return model;
//    }
    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/patients/email/{email}")
    public ResponseEntity<Patient> getPatientByEmail(@PathVariable String email) {
        Patient patient = patientService.getPatientByEmail(email);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patient);
    }
    

    @PutMapping("patients/{identifier}")
    public ResponseEntity<?> updatePatient(@PathVariable("identifier") String identifier, @RequestBody Patient Patient) {
        Patient updatedPatient;
        if (identifier.contains("@")) {
            updatedPatient = patientService.updatePatientByEmail(identifier, Patient);
        } else {
            updatedPatient = patientService.updatePatientById(identifier, Patient);
        }
        if (updatedPatient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Patient updated successfully");
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        boolean deleted = patientService.deletePatient(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}


