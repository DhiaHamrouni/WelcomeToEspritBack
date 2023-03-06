package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.ResultServiceInerface;
import com.example.welcometoesprit.entities.CondidatOffre;
import com.example.welcometoesprit.entities.Offre;
import com.example.welcometoesprit.entities.Result;
import com.example.welcometoesprit.entities.natija;
import com.example.welcometoesprit.repository.CandidatoffreRepository;
import com.example.welcometoesprit.repository.OffreRepository;
import com.example.welcometoesprit.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ResultServiceImp extends BaseServiceImp<Result,Integer> implements ResultServiceInerface {

    @Autowired
    ResultRepository resultRepository;
    CandidatoffreRepository candidatoffreRepository;
    OffreRepository offreRepository;

    public String assignresult(Integer id_candidat,Integer id_offre){
        Offre offre=offreRepository.getReferenceById(id_offre);
        CondidatOffre condidatOffre =candidatoffreRepository.getReferenceById(id_candidat);
        Integer i=0;
        if (offre.getResultList().size()<3){
            return ("Your Application is still under process");
        }
        for (Result element: offre.getResultList()){
            if (element.getResult().equals(natija.PASSED)){
                i++;
            }
            else {
                i--;
            }
        }
        if (i>3){
            condidatOffre.getResult().setResult(natija.PASSED);
            condidatOffre.setEnableCondidatOffre(true);
            return ("Congtulations! You have Succeded");
        }
        else {
            condidatOffre.getResult().setResult(natija.NOT_PASSED);
            candidatoffreRepository.deleteById(id_candidat);
            return ("Sorry , Your Application was denied better luck next time");
        }
    }
}
