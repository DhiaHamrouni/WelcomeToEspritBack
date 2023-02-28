package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.UniversityServiceImp;
import com.example.welcometoesprit.entities.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uni")
@CrossOrigin("*")
public class UniversityController extends BaseController<University,Integer> {

    @Autowired
    UniversityServiceImp universityServiceImp;
}
