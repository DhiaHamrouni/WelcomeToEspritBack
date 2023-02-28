package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.RdvoffreServiceInterface;
import com.example.welcometoesprit.entities.RDV_offre;
import com.example.welcometoesprit.repository.RdvoffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RdvoffreServiceImp extends BaseServiceImp<RDV_offre,Integer> implements RdvoffreServiceInterface {
        @Autowired
    RdvoffreRepository rdvoffreRepository;
}
