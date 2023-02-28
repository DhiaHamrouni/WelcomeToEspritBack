package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.RealisationServiceImp;
import com.example.welcometoesprit.entities.Realisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/realisation")
@CrossOrigin("*")
public class RealisationController extends BaseController<Realisation,Integer>{

    @Autowired
    RealisationServiceImp realisationServiceImp;
}
