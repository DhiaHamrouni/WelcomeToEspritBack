package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.entities.Jury;
import com.example.welcometoesprit.entities.RDV_offre;
import com.example.welcometoesprit.repository.JuryRepository;
import com.example.welcometoesprit.repository.RdvoffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JuryServiceImp extends BaseServiceImp<Jury,Integer> {
    @Autowired
    JuryRepository juryRepository;
    @Autowired
    RdvoffreServiceImp rdvoffreServiceImp;

    public Jury AddandAssign(Jury jury,Integer idrdv){
        RDV_offre rdv=rdvoffreServiceImp.retrieve(idrdv);
        List<Jury> juryList=rdv.getJuries();
        for (Jury element : juryList){
            for (RDV_offre rdv_offre:element.getRdvOffreList()){
                if (rdv_offre.equals(rdv)){
                    throw new IllegalStateException("jurer already assigned to this appointment");
                }
            }
        }
        juryList.add(jury);
        rdv.setJuries(juryList);
        juryRepository.save(jury);
        return jury;
    }
}
