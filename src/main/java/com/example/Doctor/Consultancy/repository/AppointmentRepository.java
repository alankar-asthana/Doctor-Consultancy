package com.example.Doctor.Consultancy.repository;

import com.example.Doctor.Consultancy.models.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    List<Appointment> findByDoctorEmailAndAppointmentDate(String doctorEmail, Date appointmentDate);

    List<Appointment> findByPatientEmail(String email);

    List<Appointment> findByDoctorEmail(String email);
}
