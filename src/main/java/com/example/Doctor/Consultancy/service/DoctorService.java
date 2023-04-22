package com.example.Doctor.Consultancy.service;

import com.example.Doctor.Consultancy.models.Doctor;
import com.example.Doctor.Consultancy.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    //@Autowired
    //private BCryptPasswordEncoder passwordEncoder;

    public Doctor createDoctor(Doctor doctor) {
        //doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(String id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Optional<List<Doctor>> getDoctorsBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }


    public Doctor updateDoctorById(String id, Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findById(id).orElse(null);
        if (existingDoctor == null) {
            return null;
        }
        existingDoctor.setName(doctor.getName() != null ? doctor.getName() : existingDoctor.getName());
        existingDoctor.setEmail(doctor.getEmail() != null ? doctor.getEmail() : existingDoctor.getEmail());
        existingDoctor.setSpecialty(doctor.getSpecialty() != null ? doctor.getSpecialty() : existingDoctor.getSpecialty());
        existingDoctor.setPassword(doctor.getPassword() != null ? doctor.getPassword() : existingDoctor.getPassword());
        existingDoctor.setDegree(doctor.getDegree() != null ? doctor.getDegree() : existingDoctor.getDegree());
        existingDoctor.setLicenseNumber(doctor.getLicenseNumber() != null ? doctor.getLicenseNumber() : existingDoctor.getLicenseNumber());
        existingDoctor.setAddress(doctor.getAddress() != null ? doctor.getAddress() : existingDoctor.getAddress());
        existingDoctor.setPhoneNumber(doctor.getPhoneNumber() != null ? doctor.getPhoneNumber() : existingDoctor.getPhoneNumber());
        return doctorRepository.save(existingDoctor);
    }
    public Doctor updateDoctorByEmail(String email, Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findByEmail(email).orElse(null);
        if (existingDoctor == null) {
            return null;
        }
        existingDoctor.setName(doctor.getName() != null ? doctor.getName() : existingDoctor.getName());
        existingDoctor.setEmail(doctor.getEmail() != null ? doctor.getEmail() : existingDoctor.getEmail());
        existingDoctor.setSpecialty(doctor.getSpecialty() != null ? doctor.getSpecialty() : existingDoctor.getSpecialty());
        existingDoctor.setPassword(doctor.getPassword() != null ? doctor.getPassword() : existingDoctor.getPassword());
        existingDoctor.setDegree(doctor.getDegree() != null ? doctor.getDegree() : existingDoctor.getDegree());
        existingDoctor.setLicenseNumber(doctor.getLicenseNumber() != null ? doctor.getLicenseNumber() : existingDoctor.getLicenseNumber());
        existingDoctor.setAddress(doctor.getAddress() != null ? doctor.getAddress() : existingDoctor.getAddress());
        existingDoctor.setPhoneNumber(doctor.getPhoneNumber() != null ? doctor.getPhoneNumber() : existingDoctor.getPhoneNumber());
        return doctorRepository.save(existingDoctor);
    }
    public boolean deleteDoctor(String id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isPresent()) {
            doctorRepository.delete(doctor.get());
        } else {
            throw new NoSuchElementException("Doctor not found with id : " + id);
        }
        return false;
    }

    public void deactivateDoctorAccount(String doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    public Doctor authenticate(String email, String password) {
        Optional<Doctor> optionalDoctor = doctorRepository.findByEmailAndPassword(email, password);

        if (optionalDoctor.isPresent()) {
            return optionalDoctor.get();
        } else {
            return null;
        }
    }

    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email).orElse(null);
    }
}

