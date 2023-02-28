package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.AdServiceInterface;
import com.example.welcometoesprit.entities.Ads;
import com.example.welcometoesprit.repository.AdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdServiceImp extends BaseServiceImp<Ads,Integer> implements AdServiceInterface {

    @Autowired
    AdsRepository adsRepository;
}
