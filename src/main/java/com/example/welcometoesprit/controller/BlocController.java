package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.BlocServiceImp;
import com.example.welcometoesprit.entities.Bloc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bloc")
@CrossOrigin("*")
public class BlocController extends BaseController<Bloc,Integer> {
    @Autowired
    BlocServiceImp blocServiceImp;
}
