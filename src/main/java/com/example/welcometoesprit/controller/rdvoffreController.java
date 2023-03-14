package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.RdvoffreServiceImp;
import com.example.welcometoesprit.entities.RDV_offre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rdvoffre")
@CrossOrigin("*")
public class rdvoffreController extends BaseController<RDV_offre,Integer> {
    @Autowired
    RdvoffreServiceImp rdvoffreServiceImp;
    @PostMapping("/assign/{id1}/{id2}")
    public ResponseEntity<RDV_offre> add(@RequestBody RDV_offre e, @PathVariable("id1") Integer id1, @PathVariable("id2") Integer id2){
        return ResponseEntity.ok(rdvoffreServiceImp.AddandAssignRdvtoOffer(e,id1,id2));
    }
}

