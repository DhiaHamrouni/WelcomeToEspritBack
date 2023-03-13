package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.CandidatoffreServiceInterface;
import com.example.welcometoesprit.entities.*;
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
    OffreRepository offreRepository;
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
        if (offre.getCapacity()==0){
            throw new IllegalStateException("This offer has no vacant positions");
        }
        else {
            for (CondidatOffre element : condidatOffreList) {
                if (element.getEmail().equals(condidatOffre.getEmail())) {
                    throw new IllegalStateException(ENTITY_ALREADY_EXISTS_MSG);
                }
            }
            condidatOffreList.add(condidatOffre);
            offre.setCondidatOffres(condidatOffreList);
            offre.setCapacity(offre.getCapacity() - 1);
            candidatoffreRepository.save(condidatOffre);
            return condidatOffre;
        }
    }

    //New
    public Result calculResult(Integer idoff, Integer idcandidat){
        CondidatOffre condidatOffre=candidatoffreRepository.getReferenceById(idcandidat);
        Offre offre=offreRepository.getReferenceById(idoff);
        if (offre.getCondidatOffres().contains(condidatOffre)){
            Result result=condidatOffre.getResult();
            int a = 0,b=0,c=0,d=0;
            int size=result.getJuries().size();
            for (Jury element:result.getJuries()){
                for (Criteria criteria:element.getCriteriaList()){
                    if (criteria.getQuestion().equals(Questions.TECHNICAL)){
                        a+=criteria.getValue()*2;
                    }
                    if (criteria.getQuestion().equals(Questions.COMMUNICATION)){
                        b+=criteria.getValue()*2;
                    }
                    if (criteria.getQuestion().equals(Questions.BODY_LANGUAGE)){
                        c+=criteria.getValue()*1;
                    }
                    if (criteria.getQuestion().equals(Questions.PRESENTATION)){
                        d+=criteria.getValue()*1;
                    }
                }
            }
            if (((a/size)+(b/size)+(c/size)+(d/size)/4)>5){
                result.setResult(natija.PASSED);
                condidatOffre.setResult(result);
                return result;
            }
            else {
                result.setResult(natija.NOT_PASSED);
                return result;
            }
        }
        else {
            throw new  IllegalStateException("You have yet to apply for this offer");
        }
    }




}
