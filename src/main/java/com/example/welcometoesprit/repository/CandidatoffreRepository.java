package com.example.welcometoesprit.repository;

import com.example.welcometoesprit.entities.CondidatOffre;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoffreRepository extends BaseRepository<CondidatOffre,Integer> {
    CondidatOffre findByEmail(String Email);
    int enableCondidatOffre(String email);
}
