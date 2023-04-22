package com.example.Doctor.Consultancy.service;

import com.example.Doctor.Consultancy.models.Appointment;
import com.example.Doctor.Consultancy.models.Doctor;
import com.example.Doctor.Consultancy.models.Patient;
import com.example.Doctor.Consultancy.repository.DoctorRepository;
import com.example.Doctor.Consultancy.repository.AppointmentRepository;
import com.example.Doctor.Consultancy.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Method to save the appointment in the database
    public void saveAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    // Method to check if the appointment slot is available
    public boolean isAppointmentSlotAvailable(String doctorEmail, Date appointmentDate, Time appointmentTime) {
        Doctor doctor = doctorRepository.findByEmail(doctorEmail);
        if (doctor.isPresent()) {
            List<Appointment> appointments = appointmentRepository.findByDoctorEmailAndAppointmentDate(doctorEmail, appointmentDate);
            int count=0;
            for (Appointment appointment : appointments) {
//                if (appointment.getAppointmentTime().equals(appointmentTime)) {
//                    return false;
//                }
                count++;
                if(count==6)
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    // Method to set the appointment slot as unavailable
//    public void setAppointmentSlotUnavailable(String doctorEmail, Date appointmentDate, Time appointmentTime) {
//        Optional<Doctor> doctor = doctorRepository.findByEmail(doctorEmail);
//        if (doctor.isPresent()) {
//            List<Appointment> appointments = appointmentRepository.findByDoctorEmailAndAppointmentDate(doctorEmail, appointmentDate);
//            for (Appointment appointment : appointments) {
//                if (appointment.getAppointmentTime().equals(appointmentTime)) {
//                    appointment.setAppointmentStatus(Appointment.AppointmentStatus.UNAVAILABLE);
//                    appointmentRepository.save(appointment);
//                }
//            }
//        }
//    }

    // Method to get the patient by email
    public Patient getPatientByEmail(String patientEmail) {
        return patientRepository.findByEmail(patientEmail);
    }

    // Method to get the doctor by email
    public Doctor getDoctorByEmail(String doctorEmail) {
        return doctorRepository.findByEmail(doctorEmail);
    }
    public List<Appointment> getAppointmentByPatientEmail(String email) {
        return appointmentRepository.findByPatientEmail(email);
    }

    public List<Appointment> getAppointmentByDoctorEmail(String email) {
        return appointmentRepository.findByDoctorEmail(email);
    }

    public Optional<Appointment> getAppointmentById(String appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    public void deleteAppointment(String appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

}

