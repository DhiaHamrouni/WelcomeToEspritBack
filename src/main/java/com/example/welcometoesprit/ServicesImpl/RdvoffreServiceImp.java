package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.RdvoffreServiceInterface;
import com.example.welcometoesprit.entities.CondidatOffre;
import com.example.welcometoesprit.entities.Offre;
import com.example.welcometoesprit.entities.RDV_offre;
import com.example.welcometoesprit.repository.CandidatoffreRepository;
import com.example.welcometoesprit.repository.OffreRepository;
import com.example.welcometoesprit.repository.RdvoffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RdvoffreServiceImp extends BaseServiceImp<RDV_offre,Integer> implements RdvoffreServiceInterface {
        @Autowired
    RdvoffreRepository rdvoffreRepository;
        OffreRepository offreRepository;
        CandidatoffreRepository candidatoffreRepository;

        public RDV_offre AddandAssignRdvtoOffer(RDV_offre rdv_offre,Integer id_offre,Integer id_co){
            Offre offre=offreRepository.getReferenceById(id_offre);
            CondidatOffre condidatOffre=candidatoffreRepository.getReferenceById(id_co);
            if (rdv_offre.getCondidatOffre().equals(condidatOffre) || rdv_offre.getOffre().equals(offre)){
                throw new IllegalStateException("RDV exists already");
            }
            if (offre.getRdvOffreList().contains(rdv_offre)){
                throw new IllegalStateException("RDV exists already for this offer");
            }
            if (condidatOffre.getRdv_offre().getSchedule().equals(rdv_offre.getSchedule())){
                throw new IllegalStateException("Client already has an appointment at this date");
            }
            rdv_offre.setOffre(offre);
            rdv_offre.setCondidatOffre(condidatOffre);
            condidatOffre.setRdv_offre(rdv_offre);
            rdvoffreRepository.save(rdv_offre);
            return rdv_offre;
        }
}
