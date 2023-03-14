package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.entities.Criteria;
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
        List<RDV_offre> rdvOffreList=jury.getRdvOffreList();
        List<Jury> juries=rdv.getJuries();
        if (rdvOffreList.contains(rdv)){
            throw new IllegalStateException("juror already assigned to this appointment");
        }
        if (juries.size()>=3){
            throw new IllegalStateException("this RDV already has enough jurors");
        }
        else {
            for (RDV_offre element:rdvOffreList) {
                if (element.getSchedule().equals(rdv.getSchedule())) {
                    throw new IllegalStateException("juror already has another appointment at this time" + element.getSchedule());
                }
            }
            rdvOffreList.add(rdv);
            jury.setRdvOffreList(rdvOffreList);
            juries.add(jury);
            juryRepository.save(jury);
            return jury;
        }
    }
    public List<Criteria> criteriaList(Integer id){
        Jury jury=juryRepository.getReferenceById(id);
        return jury.getCriteriaList();
    }
}
