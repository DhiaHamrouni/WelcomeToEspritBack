package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.InterviewServiceImp;
import com.example.welcometoesprit.entities.Interview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interview")
@CrossOrigin("*")
public class InterviewController extends BaseController<Interview,Integer>{

    @Autowired
    InterviewServiceImp interviewServiceImp;
}
