package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.CandidatoffreServiceImp;
import com.example.welcometoesprit.entities.CondidatOffre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidatoffre")
@CrossOrigin("*")
public class CandidatoffreController extends BaseController<CondidatOffre,Integer> {
    @Autowired
    CandidatoffreServiceImp candidatoffreServiceImp;
}
