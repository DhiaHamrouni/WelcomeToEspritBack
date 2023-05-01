package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.entities.Criteria;
import com.example.welcometoesprit.entities.Jury;
import com.example.welcometoesprit.repository.CriteriaRepository;
import com.example.welcometoesprit.repository.JuryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriteriaServiceImp extends BaseServiceImp<Criteria,Integer> {
    @Autowired
    CriteriaRepository criteriaRepository;
    @Autowired
    JuryRepository juryRepository;
    public Criteria JudgeaddCriteria(Criteria crit,Integer id_judge){
        Jury jury=juryRepository.getReferenceById(id_judge);
        for (Criteria element:jury.getCriteriaList()){
            if (element.getQuestion().equals(crit.getQuestion())){
                throw new IllegalStateException("This Criteria is already added");
            }
        }
        crit.setJury(jury);
        criteriaRepository.save(crit);
        juryRepository.save(jury);
        return crit;
    }
}
