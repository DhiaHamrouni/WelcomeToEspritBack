package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.RdvoffreServiceImp;
import com.example.welcometoesprit.entities.RDV_offre;
import org.springframework.beans.factory.annotation.Autowired;

public class rdvoffreController extends BaseController<RDV_offre,Integer> {
    @Autowired
    RdvoffreServiceImp rdvoffreServiceImp;
}
