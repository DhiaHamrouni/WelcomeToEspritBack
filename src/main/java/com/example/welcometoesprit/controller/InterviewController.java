package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.InterviewServiceImp;
import com.example.welcometoesprit.entities.Interview;
import com.example.welcometoesprit.entities.Role;
import com.example.welcometoesprit.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/interview")
@CrossOrigin("*")
public class InterviewController extends BaseController<Interview,Integer>{

    @Autowired
    InterviewServiceImp interviewServiceImp;


    @GetMapping("/sendMailToStudent/{idUser}")
    public String sendMailInterviewDetails(@PathVariable Integer idUser){
        interviewServiceImp.sendInterviewDetails(idUser);
        return "Interview email sent!";

    }

    @PutMapping("assignInterviewToStudent/{idStudent}")
    public String assignInterviewToStudent(@PathVariable Integer idStudent,@RequestBody Interview interview){
        LocalDateTime localDateTime = interview.getDateInterview();
        return interviewServiceImp.assignInterviewToStudent(idStudent,localDateTime);
    }
}
