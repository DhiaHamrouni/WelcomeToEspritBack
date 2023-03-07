package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.OffreServiceInterface;
import com.example.welcometoesprit.entities.CondidatOffre;
import com.example.welcometoesprit.entities.Offre;
import com.example.welcometoesprit.entities.Type;
import com.example.welcometoesprit.repository.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffreServiceImp extends BaseServiceImp<Offre,Integer> implements OffreServiceInterface {
    @Autowired
    OffreRepository offreRepository;

    public String statsoffers(){
        List<Offre> offreList=offreRepository.findAll();
        int teach=0;
        int agent=0;
        int both=offreList.size();
        for (Offre element: offreList){
            if (element.getType().equals(Type.TEACHING)){
                teach++;
            }
            else {
                agent++;
            }
        }
        return "The number of teaching offers available for now makes up for "+(teach/both)*100+"% of the total existing offers \n and the number of adiministratif offers is "+(agent/both)*100+"%";
    }

    public String numberofApplicationperOffre(Offre offre){
        return "the number of applications to the offre "+offre.getTitle()+" is "+offre.getCondidatOffres().size();
    }

}
