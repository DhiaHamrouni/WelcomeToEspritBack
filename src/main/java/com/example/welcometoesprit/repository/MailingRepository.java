package com.example.welcometoesprit.repository;

import com.example.welcometoesprit.entities.Mailingcontent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailingRepository extends BaseRepository<Mailingcontent,Integer> {

    List<Mailingcontent> getAllByToEmail(String email);

}
