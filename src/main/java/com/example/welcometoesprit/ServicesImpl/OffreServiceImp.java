package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.OffreServiceInterface;
import com.example.welcometoesprit.entities.Offre;
import com.example.welcometoesprit.repository.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class OffreServiceImp extends BaseServiceImp<Offre,Integer> implements OffreServiceInterface {
    @Autowired
    OffreRepository offreRepository;

}
