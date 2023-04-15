package com.example.Doctor.Consultancy.repository;
import com.example.Doctor.Consultancy.models.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {

    Optional<List<Doctor>> findBySpecialty(String specialty);
    Optional<Doctor> findByEmail(String email);

}
