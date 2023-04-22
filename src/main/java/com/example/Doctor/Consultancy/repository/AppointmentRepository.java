package com.example.Doctor.Consultancy.repository;

import com.example.Doctor.Consultancy.models.Appointment;
import com.example.Doctor.Consultancy.models.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByEmail();
    String getPatientNameByEmail(String patientEmail);

    List<Appointment> findByDoctorEmailAndAppointmentDate(String doctorEmail, Date appointmentDate);

    List<Appointment> findByPatientEmail(String email);

    List<Appointment> findByDoctorEmail(String email);
}
