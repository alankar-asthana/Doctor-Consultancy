package com.example.Doctor.Consultancy.api;
import com.example.Doctor.Consultancy.models.Appointment;
import com.example.Doctor.Consultancy.models.Doctor;
import com.example.Doctor.Consultancy.models.Patient;
import com.example.Doctor.Consultancy.service.AppointmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private HttpSession session;


    @PostMapping("/save-appointment")
    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment, RedirectAttributes attributes)
            throws UnsupportedEncodingException {
        String patientEmail = appointment.getPatientEmail();
        //System.out.println("Patient Email: "+patientEmail);
        String doctorEmail = appointment.getDoctorEmail();
        //System.out.println("Doctor Email: "+doctorEmail);
        String appointmentDate = appointment.getAppointmentDate();
//        Time appointmentTime = appointment.getAppointmentTime();

        // check if patient exists
        Patient patient = appointmentService.getPatientByEmail(patientEmail);
        if (patient == null) {
            // set the error message in the flash attribute
            attributes.addFlashAttribute("error", "Patient Not found. Please first register.");

            // set the error message in session storage
            session.setAttribute("errorMessage", "Patient Not found. Please first register.");

            // redirect the user to the specified page
            return "redirect:/patient-register";

        }


        // check if doctor exists
        Doctor doctor = appointmentService.getDoctorByEmail(doctorEmail);
        if (doctor == null) {
            // set the error message in the flash attribute
            attributes.addFlashAttribute("error", "Doctor Not found. Please choose another doctor.");
            session.setAttribute("redirectUrl", "/specialty");
            // redirect the user to the specified page
            return "redirect:/specialty";
        }

        // check if appointment slot is available
        if (!appointmentService.isAppointmentSlotAvailable(doctorEmail, appointmentDate)) {
            // set the error message in the flash attribute
            attributes.addFlashAttribute("error", "Slot not available. Please choose another doctor.");
            session.setAttribute("redirectUrl", "/specialty");
            // redirect the user to the specified page
            return "redirect:/specialty";
        }



        // create new appointment object
        Appointment newappointment = new Appointment();
        //newappointment.setId(UUID.randomUUID().toString());
        newappointment.setPatientName(patient.getName());
        newappointment.setPatientEmail(patient.getEmail());
        newappointment.setDoctorName(doctor.getName());
        newappointment.setDoctorEmail(doctor.getEmail());
        newappointment.setAppointmentDate(appointment.getAppointmentDate());
        //newappointment.setAppointmentTime(appointmentTime);
        newappointment.setVideoLink(newappointment.generateVideoCallLink());
        newappointment.setSymptoms(appointment.getSymptoms());

        // save appointment
        appointmentService.saveAppointment(newappointment);

        // set appointment slot as unavailable
        //appointmentService.setAppointmentSlotUnavailable(doctorEmail, appointmentDate, appointmentTime);
            attributes.addFlashAttribute("success", "Appointment Successful");
            return "redirect:/patient-login";
    }

    @GetMapping("/appointments/patient/{email}")
    public ResponseEntity<Optional<List<Appointment>>> getAppointmentsByPatientEmail(@PathVariable String email) {
        //System.out.println("Email: "+email);
        return new ResponseEntity<Optional<List<Appointment>>>(appointmentService.getAppointmentByPatientEmail(email), HttpStatus.OK);
    }

    @GetMapping("/appointments/doctor/{email}")
    public ResponseEntity<Optional<List<Appointment>>> getAppointmentsByDoctorEmail(@PathVariable String email) {
        //System.out.println("Email: "+email);
        return new ResponseEntity<Optional<List<Appointment>>>(appointmentService.getAppointmentByDoctorEmail(email), HttpStatus.OK);
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Object> cancelAppointment(@PathVariable("id") String appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentService.getAppointmentById(appointmentId);

        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();

            // set appointment slot as available
            //appointmentService.setAppointmentSlotAvailable(appointment.getDoctorEmail(), appointment.getAppointmentDate(), appointment.getAppointmentTime());

            // delete appointment from database
            appointmentService.deleteAppointment(appointmentId);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

