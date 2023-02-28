package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.DepartementServiceImp;
import com.example.welcometoesprit.entities.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departement")
@CrossOrigin("*")
public class DepartementController extends BaseController<Departement,Integer> {
    @Autowired
    DepartementServiceImp departementServiceImp;
}
