package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.ClassroomServiceImp;
import com.example.welcometoesprit.entities.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@PreAuthorize("hasRole('ROLE_TEACHER')")

@RestController
@RequestMapping("/teacher/classroom")
@CrossOrigin("*")
public class ClassroomController extends BaseController<Classroom,Integer>{

    @Autowired
    ClassroomServiceImp classroomServiceImp;
}
