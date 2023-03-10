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
    JuryRepository juryRepository;
    public Criteria JudgeaddCriteria(Criteria crit,Integer id_judge){
        Jury jury=juryRepository.getReferenceById(id_judge);
        for (Criteria element:jury.getCriteriaList()){
            if (element.getQuestion().equals(crit.getQuestion())){
                throw new IllegalStateException("This Criteria is already added");
            }
        }
        List<Criteria> criteria=jury.getCriteriaList();
        criteria.add(crit);
        jury.setCriteriaList(criteria);
        criteriaRepository.save(crit);
        return crit;
    }
}
