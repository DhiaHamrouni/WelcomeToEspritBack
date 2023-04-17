package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.CriteriaServiceImp;
import com.example.welcometoesprit.entities.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/criteria")
@CrossOrigin("*")
public class CriteriaController extends BaseController<Criteria,Integer> {
    @Autowired
    CriteriaServiceImp criteriaServiceImp;

    @PostMapping("/add/{id}")
    public ResponseEntity<String> addcrit(@RequestBody Criteria crit, @PathVariable("id") Integer id){
        criteriaServiceImp.JudgeaddCriteria(crit,id);
        return ResponseEntity.ok("OK");
    }

}
