package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.OffreServiceImp;
import com.example.welcometoesprit.entities.Offre;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offre")
@CrossOrigin("*")
@AllArgsConstructor
public class OffreController extends BaseController<Offre,Integer>{

    OffreServiceImp offreServiceImp;
}
