package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.ResultServiceInerface;
import com.example.welcometoesprit.entities.*;
import com.example.welcometoesprit.repository.CandidatoffreRepository;
import com.example.welcometoesprit.repository.OffreRepository;
import com.example.welcometoesprit.repository.RdvoffreRepository;
import com.example.welcometoesprit.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ResultServiceImp extends BaseServiceImp<Result,Integer> implements ResultServiceInerface {

    @Autowired
    ResultRepository resultRepository;
}
