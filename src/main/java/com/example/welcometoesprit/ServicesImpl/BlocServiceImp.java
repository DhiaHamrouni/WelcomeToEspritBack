package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.BlocServiceInterface;
import com.example.welcometoesprit.entities.Bloc;
import com.example.welcometoesprit.repository.BlocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlocServiceImp extends BaseServiceImp<Bloc,Integer> implements BlocServiceInterface {
    @Autowired
    BlocRepository blocRepository;
}
