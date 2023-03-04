package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.CandidatoffreServiceImp;
import com.example.welcometoesprit.auth.AuthenticationResponse;
import com.example.welcometoesprit.entities.Classroom;
import com.example.welcometoesprit.entities.CondidatOffre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidatoffre")
@CrossOrigin("*")
public class CandidatoffreController extends BaseController<CondidatOffre,Integer>{
    @Autowired
    CandidatoffreServiceImp candidatoffreServiceImp;
    @PostMapping("/assign/{id}")
    public ResponseEntity<CondidatOffre> add(@RequestBody CondidatOffre e,@PathVariable("id") Integer id){
        System.out.println(e);
        return ResponseEntity.ok(candidatoffreServiceImp.addapplicationandassigntooffer(e,id));
    }
}
