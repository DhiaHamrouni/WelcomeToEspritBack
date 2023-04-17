package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.OffreServiceImp;
import com.example.welcometoesprit.entities.Comment;
import com.example.welcometoesprit.entities.CondidatOffre;
import com.example.welcometoesprit.entities.Offre;
import com.example.welcometoesprit.repository.OffreRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/offre")
@CrossOrigin("*")
@AllArgsConstructor
public class OffreController extends BaseController<Offre,Integer>{
    @Autowired
    OffreServiceImp offreServiceImp;
    @GetMapping("/stats")
    public String stats(){
        return offreServiceImp.statsoffers();
    }
    @GetMapping("/getAll")
    public List<Offre> getAll(){
        List<Offre> offreList=new ArrayList<>();
        for (Offre element: offreServiceImp.retrieveAll()){
            if (element.getCapacity()>0){
                offreList.add(element);
            }
        }
        return offreList;
    }
}
