package com.example.welcometoesprit.repository;

import com.example.welcometoesprit.entities.Interview;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InterviewRepository extends BaseRepository<Interview,Integer> {
    List<Interview> findByDateInterviewIsBetween(LocalDateTime start, LocalDateTime end);

}
