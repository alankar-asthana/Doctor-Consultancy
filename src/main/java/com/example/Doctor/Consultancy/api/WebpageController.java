package com.example.Doctor.Consultancy.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebpageController {

    @GetMapping("/")
    public String redirectToHomePage(){
        return "redirect:/home-page";
    }

    @GetMapping("/home-page")
    public String ShowHomePage(){
        return "index";
    }

    @GetMapping("/specialty")
    public String ShowSpecialty(){
        return "Specialty";
    }
    @GetMapping("/doctor-list")
    public String ShowDoctorList(@PathVariable String specialty, Model model){
        model.addAttribute("specialty", specialty);
        return "DoctorList";
    }
//    @PostMapping("/appointment-form")
//    public String showAppointmentForm(@RequestParam String doctorName, @RequestParam String doctorEmail, Model model) {
//        model.addAttribute("doctorName", doctorName);
//        model.addAttribute("doctorEmail", doctorEmail);
//        return "Appointment";
//    }
    @GetMapping("/appointment-form")
    public String showAppointmentForm(@RequestParam("name") String name, @RequestParam("email") String email, Model model) {
        // Pass the doctor's name and email to the appointment form view
        model.addAttribute("doctorName", name);
        model.addAttribute("doctorEmail", email);
        return "Appointment";
    }


//    @PostMapping("/doctor-list")
//    public String ShowDoctorList(){
//        return "DoctorList";
//    }
}

