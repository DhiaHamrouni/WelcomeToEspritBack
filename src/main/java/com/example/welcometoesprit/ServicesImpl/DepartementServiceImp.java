package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.DepartementServiceInterface;
import com.example.welcometoesprit.entities.Departement;
import com.example.welcometoesprit.repository.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartementServiceImp extends BaseServiceImp<Departement,Integer> implements DepartementServiceInterface {
    @Autowired
    DepartementRepository departementRepository;
}
