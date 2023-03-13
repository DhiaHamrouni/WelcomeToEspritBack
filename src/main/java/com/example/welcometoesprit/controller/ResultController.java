package com.example.welcometoesprit.controller;

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
//    @PostMapping("/{id_candidat}/{id_offre}")
//    public String random(@PathVariable("id_candidat") Integer idc,@PathVariable("id_offre")Integer ido){
//        return resultServiceImp.assignresult(idc,ido);
//    }
}
