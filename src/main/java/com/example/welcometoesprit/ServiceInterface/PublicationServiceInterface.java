package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.Publication;
import com.example.welcometoesprit.entities.Rating;

import java.util.List;

public interface PublicationServiceInterface extends BaseServiceInterface<Publication,Integer>{
    public Publication addPublication(Publication publication ,Integer IdUser);

}
