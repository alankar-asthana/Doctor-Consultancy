package com.example.Doctor.Consultancy.api;

import com.example.Doctor.Consultancy.models.Doctor;
import com.example.Doctor.Consultancy.service.DoctorService;
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
//@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;


/////////////////////------------Updated Code----------------/////////////////
@GetMapping("/register")
public String showRegistrationForm() {
    return "DoctorForm";
}

    @PostMapping("/register")
    public String createDoctor(@ModelAttribute("doctor") Doctor doctor, RedirectAttributes attributes) {
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        if (createdDoctor != null) {
            return "redirect:/login";
        } else {
            attributes.addFlashAttribute("error", "Registration failed. Please try again.");
            return "redirect:/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "Login";
    }

//    @PostMapping("/login")
//    public String login(@ModelAttribute("doctor") Doctor doctor, RedirectAttributes attributes) {
//        Doctor authenticatedDoctor = doctorService.authenticate(doctor.getEmail(), doctor.getPassword());
//
//        if (authenticatedDoctor == null) {
//            return "redirect:/login";
//        } else {
//            return "redirect:/dashboard";
//        }
//    }
    @PostMapping("/login")
    public String login(@ModelAttribute("doctor") Doctor doctor, RedirectAttributes attributes, HttpSession session) {
        Doctor authenticatedDoctor = doctorService.authenticate(doctor.getEmail(), doctor.getPassword());

        if (authenticatedDoctor == null) {
            return "redirect:/login";
        } else {
            session.setAttribute("doctorEmail", authenticatedDoctor.getEmail());
            return "redirect:/dashboard?email=" + authenticatedDoctor.getEmail();
        }
    }
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "DoctorDashboard";
    }

///////////////////////////////////---------------------------///////////////////////////////////////////

    @GetMapping("/api/doctors")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/api/doctors/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable String id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctor);
    }
    @GetMapping("/api/doctors/email/{email}")
    public ResponseEntity<Doctor> getDoctorByEmail(@PathVariable String email) {
        Doctor doctor = doctorService.getDoctorByEmail(email);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/api/doctors/specialty/{specialty}")
    public ResponseEntity<Optional<List<Doctor>>> getDoctorsBySpecialty(@PathVariable String specialty) {
        return new ResponseEntity<Optional<List<Doctor>>>(doctorService.getDoctorsBySpecialty(specialty), HttpStatus.OK);
    }

    @PutMapping("/api/doctors/{identifier}")
    public ResponseEntity<?> updateDoctor(@PathVariable("identifier") String identifier, @RequestBody Doctor doctor) {
        Doctor updatedDoctor;
        if (identifier.contains("@")) {
            updatedDoctor = doctorService.updateDoctorByEmail(identifier, doctor);
        } else {
            updatedDoctor = doctorService.updateDoctorById(identifier, doctor);
        }
        if (updatedDoctor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Doctor updated successfully");
    }

    @DeleteMapping("/api/doctors/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable String id) {
        boolean deleted = doctorService.deleteDoctor(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
