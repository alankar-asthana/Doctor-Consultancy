package com.example.Doctor.Consultancy.api;

import com.example.Doctor.Consultancy.models.Doctor;
import com.example.Doctor.Consultancy.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/")
    public String showRegistrationForm() {
        return "DoctorForm";
    }

    @PostMapping("/register")
    public ResponseEntity<Doctor> createDoctor(@ModelAttribute Doctor doctor) {
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
    }
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
