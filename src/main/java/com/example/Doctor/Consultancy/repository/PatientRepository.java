package com.example.Doctor.Consultancy.repository;
import com.example.Doctor.Consultancy.models.Doctor;
import com.example.Doctor.Consultancy.models.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String>{
    Patient findByEmail(String email);

    Optional<Patient> findByEmailAndPassword(String email, String password);
}

