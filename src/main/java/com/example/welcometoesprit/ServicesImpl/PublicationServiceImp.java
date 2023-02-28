package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.PublicationServiceInterface;
import com.example.welcometoesprit.entities.Publication;
import com.example.welcometoesprit.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicationServiceImp extends BaseServiceImp<Publication, Integer> implements PublicationServiceInterface {

    @Autowired
    PublicationRepository publicationRepository;
}
