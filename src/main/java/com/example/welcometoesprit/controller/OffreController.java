package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.OffreServiceImp;
import com.example.welcometoesprit.entities.Comment;
import com.example.welcometoesprit.entities.Offre;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offre")
@CrossOrigin("*")
@AllArgsConstructor
public class OffreController extends BaseController<Offre,Integer>{
    
    OffreServiceImp offreServiceImp;

}
