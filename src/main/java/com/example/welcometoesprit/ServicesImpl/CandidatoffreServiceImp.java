package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.CandidatoffreServiceInterface;
import com.example.welcometoesprit.entities.CondidatOffre;
import com.example.welcometoesprit.repository.CandidatoffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidatoffreServiceImp extends BaseServiceImp<CondidatOffre,Integer> implements CandidatoffreServiceInterface {
    @Autowired
    CandidatoffreRepository candidatoffreRepository;
}
