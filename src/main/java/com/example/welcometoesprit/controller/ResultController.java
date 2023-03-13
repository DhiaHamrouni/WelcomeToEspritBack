package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.CandidatoffreServiceImp;
import com.example.welcometoesprit.ServicesImpl.ResultServiceImp;
import com.example.welcometoesprit.entities.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/result")
@CrossOrigin("*")
public class ResultController extends BaseController<Result,Integer> {
    @Autowired
    ResultServiceImp resultServiceImp;
    @Autowired
    CandidatoffreServiceImp candidatoffreServiceImp;
    @GetMapping("/{id_candidat}/{id_offre}")
    public Result random(@PathVariable("id_candidat") Integer idc,@PathVariable("id_offre")Integer ido){
        return candidatoffreServiceImp.calculResult(idc,ido);
    }
}
