package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.InterviewServiceImp;
import com.example.welcometoesprit.entities.Interview;
import com.example.welcometoesprit.entities.Role;
import com.example.welcometoesprit.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/interview")
@CrossOrigin("*")
public class InterviewController extends BaseController<Interview,Integer>{

    @Autowired
    InterviewServiceImp interviewServiceImp;

    @GetMapping("/sendEmailToStudent")
    public String sendInterviewEmail() {
        // Create a sample user object
        User user = new User();
        user.setFirstname("Nour Ajimi");
        user.setEmail("nour.ajimi.2000@gmail.com");
        user.setRole(Role.STUDENT);
        Interview interview = new Interview();
        interview.setDateInterview(LocalDateTime.now());
        //interview.setClassroom();
        user.setInterviewStudent(interview);

        // Call the sendInterviewDetails method to send the email
        interviewServiceImp.sendInterviewDetails(user);

        return "Interview email sent!";
    }
}
