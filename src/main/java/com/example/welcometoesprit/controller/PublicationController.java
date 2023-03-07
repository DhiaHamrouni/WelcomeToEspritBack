package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.PublicationServiceImp;
import com.example.welcometoesprit.entities.Publication;
import com.example.welcometoesprit.entities.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publication")
@CrossOrigin("*")
public class PublicationController extends BaseController<Publication,Integer>{

    @Autowired
    PublicationServiceImp publicationServiceImp;
}
