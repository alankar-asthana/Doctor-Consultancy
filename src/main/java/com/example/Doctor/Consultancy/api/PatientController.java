package com.example.Doctor.Consultancy.api;

import com.example.Doctor.Consultancy.models.Doctor;
import com.example.Doctor.Consultancy.models.Patient;
import com.example.Doctor.Consultancy.models.Patient;
import com.example.Doctor.Consultancy.service.PatientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class PatientController {
    @Autowired
    private PatientService patientService;

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
        return "Login";
    }

    @PostMapping("/patient-login")
    public String login(@ModelAttribute("patient") Patient patient, RedirectAttributes attributes, HttpSession session) {
        Patient authenticatedPatient = patientService.authenticate(patient.getEmail(), patient.getPassword());

        if (authenticatedPatient == null) {
            return "redirect:/login";
        } else {
            session.setAttribute("patientEmail", authenticatedPatient.getEmail());
            return "redirect:/dashboard?email=" + authenticatedPatient.getEmail();
        }
    }
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }
    @GetMapping("/patient-dashboard")
    public String showDashboard() {
        return "PatientDashboard";
    }
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
        Patient Patient = patientService.getPatientByEmail(email);
        if (Patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Patient);
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


