package com.example.welcometoesprit.repository;

import com.example.welcometoesprit.entities.Interview;
import com.example.welcometoesprit.entities.MCQ;
import org.springframework.stereotype.Repository;

@Repository
public interface McqRepository  extends BaseRepository<MCQ,Integer>{
    MCQ findByInterview(Interview interview);

}

