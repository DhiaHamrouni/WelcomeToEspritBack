package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.JuryServiceImp;
import com.example.welcometoesprit.entities.Jury;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jury")
@CrossOrigin("*")
public class JuryController extends BaseController<Jury,Integer> {
    @Autowired
    JuryServiceImp juryServiceImp;
    @PostMapping("/assign/{id}")
    public ResponseEntity<Jury> add(@RequestBody Jury e, @PathVariable("id") Integer id){
        return ResponseEntity.ok(juryServiceImp.AddandAssign(e,id));
    }
}
