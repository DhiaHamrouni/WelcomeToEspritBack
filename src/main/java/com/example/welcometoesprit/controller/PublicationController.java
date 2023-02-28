package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.PublicationServiceImp;
import com.example.welcometoesprit.entities.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publication")
@CrossOrigin("*")
public class PublicationController extends BaseController<Publication,Integer>{

    @Autowired
    PublicationServiceImp publicationServiceImp;
}
