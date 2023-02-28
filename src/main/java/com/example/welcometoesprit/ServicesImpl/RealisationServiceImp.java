package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.RealisationServiceInterface;
import com.example.welcometoesprit.entities.Realisation;
import com.example.welcometoesprit.repository.RealisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealisationServiceImp extends BaseServiceImp<Realisation,Integer> implements RealisationServiceInterface {
    @Autowired
    RealisationRepository realisationRepository ;
}
