package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.UniversityServiceInterface;
import com.example.welcometoesprit.entities.University;
import com.example.welcometoesprit.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityServiceImp extends BaseServiceImp<University,Integer> implements UniversityServiceInterface {
        @Autowired
    UniversityRepository universityRepository;
}
