package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.ComplaintServiceImp;
import com.example.welcometoesprit.entities.Complaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/complaint")
@CrossOrigin("*")
public class ComplaintController extends BaseController<Complaint,Integer>{

    @Autowired
    ComplaintServiceImp complaintServiceImp;
}
