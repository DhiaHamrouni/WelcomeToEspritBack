package com.example.welcometoesprit.repository;

import com.example.welcometoesprit.entities.Classroom;
import com.example.welcometoesprit.entities.Interview;
import com.example.welcometoesprit.entities.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

import java.util.List;

@Repository
public interface InterviewRepository extends BaseRepository<Interview,Integer> {
    List<Interview> findByScheduledTime(LocalDateTime scheduledTime);

    List<Interview> findByEvaluatorAndScheduledTimeBetween(User evaluator, Date scheduledTime, Date nextThirtyMinutes);

    List<Interview> findByClassroomInterviewAndScheduledTimeBetween(Classroom classroom, Date scheduledTime, Date nextThirtyMinutes);

    List<Interview> findByDateInterviewIsBetween(LocalDateTime start, LocalDateTime end);

}
