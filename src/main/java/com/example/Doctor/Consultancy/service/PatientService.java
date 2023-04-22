package com.example.Doctor.Consultancy.service;

import com.example.Doctor.Consultancy.models.Doctor;
import com.example.Doctor.Consultancy.models.Patient;
import com.example.Doctor.Consultancy.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public Patient createPatient(Patient patient){
        return patientRepository.save(patient);
    }

    public Patient getPatientById(String id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.findById(email).orElse(null);
    }

    public Patient updatePatientByEmail(String identifier, Patient patient) {
        Patient existingPatient = patientRepository.findByEmail(identifier);
        if (existingPatient == null) {
            return null;
        }
        existingPatient.setName(patient.getName() != null ? patient.getName() : existingPatient.getName());
        existingPatient.setEmail(patient.getEmail() != null ? patient.getEmail() : existingPatient.getEmail());
        existingPatient.setPassword(patient.getPassword() != null ? patient.getPassword() : existingPatient.getPassword());
        existingPatient.setAddress(patient.getAddress() != null ? patient.getAddress() : existingPatient.getAddress());
        existingPatient.setMobileno(patient.getMobileno() != null ? patient.getMobileno() : existingPatient.getMobileno());
        return patientRepository.save(existingPatient);
    }

    public Patient updatePatientById(String identifier, Patient patient) {
        Patient existingPatient = patientRepository.findById(identifier).orElse(null);
        if (existingPatient == null) {
            return null;
        }
        existingPatient.setName(patient.getName() != null ? patient.getName() : existingPatient.getName());
        existingPatient.setEmail(patient.getEmail() != null ? patient.getEmail() : existingPatient.getEmail());
        existingPatient.setPassword(patient.getPassword() != null ? patient.getPassword() : existingPatient.getPassword());
        existingPatient.setAddress(patient.getAddress() != null ? patient.getAddress() : existingPatient.getAddress());
        existingPatient.setMobileno(patient.getMobileno() != null ? patient.getMobileno() : existingPatient.getMobileno());
        return patientRepository.save(existingPatient);
    }

    public boolean deletePatient(String id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            patientRepository.delete(patient.get());
        } else {
            throw new NoSuchElementException("Patient not found with id : " + id);
        }
        return false;
    }

    public void deactivateDoctorAccount(String patientId) {
        patientRepository.deleteById(patientId);
    }

    public Patient authenticate(String email, String password) {
        Optional<Patient> optionalPatient = patientRepository.findByEmailAndPassword(email, password);

        if (optionalPatient.isPresent()) {
            return optionalPatient.get();
        } else {
            return null;
        }
    }
}

