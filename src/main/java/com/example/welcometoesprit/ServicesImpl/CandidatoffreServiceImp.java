package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.CandidatoffreServiceInterface;
import com.example.welcometoesprit.entities.CondidatOffre;
import com.example.welcometoesprit.entities.Offre;
import com.example.welcometoesprit.repository.CandidatoffreRepository;
import com.example.welcometoesprit.repository.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CandidatoffreServiceImp extends BaseServiceImp<CondidatOffre,Integer> implements CandidatoffreServiceInterface {
    @Autowired
    CandidatoffreRepository candidatoffreRepository;
    private final static String ENTITY_NOT_FOUND_MSG = "entity with email %s not found";
    private final static String ENTITY_ALREADY_CONFIRMED_MSG = "entity with email %s is already confirmed";
    private final static String ENTITY_CONFIRMATION_EXPIRED_MSG = "entity with email %s confirmation token has expired";
    private final static String ENTITY_ALREADY_EXISTS_MSG = "entity with email already exists";
    private final List<String> liste=new ArrayList<>(Arrays.asList("entity with email %s already exists!"));
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ConfirmationTokenService confirmationTokenRepository;
    @Autowired
    private OffreServiceImp offreServiceImp;


    public CondidatOffre addapplicationandassigntooffer(CondidatOffre condidatOffre, int id_offre){
        Offre offre=offreServiceImp.retrieve(id_offre);
        List<CondidatOffre> condidatOffreList=offre.getCondidatOffres();
        /*if (condidatOffreList.contains(condidatOffre)){
            throw new IllegalStateException(ENTITY_ALREADY_EXISTS_MSG);
        }*/
        for (CondidatOffre element : condidatOffreList){
            System.out.println(element.getEmail());
            System.out.println(condidatOffre.getEmail());
            if (element.getEmail().equals(condidatOffre.getEmail())){
                throw new IllegalStateException(ENTITY_ALREADY_EXISTS_MSG);
            }
        }
        condidatOffreList.add(condidatOffre);
        offre.setCondidatOffres(condidatOffreList);
        candidatoffreRepository.save(condidatOffre);
        return condidatOffre;
    }
}
