package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.CriteriaServiceImp;
import com.example.welcometoesprit.entities.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/criteria")
@CrossOrigin("*")
public class CriteriaController extends BaseController<Criteria,Integer> {
    @Autowired
    CriteriaServiceImp criteriaServiceImp;
}
