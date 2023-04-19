package com.example.welcometoesprit.repository;

import com.example.welcometoesprit.dto.InterviewDTO;
import com.example.welcometoesprit.entities.Classroom;
import com.example.welcometoesprit.entities.Interview;
import com.example.welcometoesprit.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

import java.util.List;

@Repository
public interface InterviewRepository extends BaseRepository<Interview,Integer> {
    List<Interview> findByScheduledTime(LocalDateTime scheduledTime);

    List<Interview> findByEvaluatorAndScheduledTimeBetween(User evaluator, Date scheduledTime, Date nextThirtyMinutes);

    List<Interview> findByClassroomInterviewAndScheduledTimeBetween(Classroom classroom, Date scheduledTime, Date nextThirtyMinutes);

    List<Interview> findByDateInterviewIsBetween(Date start, Date end);

//    @Query("SELECT i.idInterview, u.firstname, u.lastname, s.firstname,s.lastname, i.dateInterview,i.heureInterview " +
//            "FROM Interview i " +
//            "JOIN User u ON i.evaluator.id = u.id AND u.role = 'TEACHER' " +
//            "JOIN User s ON s.interviewStudent.idInterview = i.idInterview AND s.role = 'STUDENT'" )
    @Query("SELECT new com.example.welcometoesprit.dto.InterviewDTO(i.idInterview, u.firstname, u.lastname, s.firstname,s.lastname, i.dateInterview,i.heureInterview) " +
            "FROM Interview i " +
            "JOIN User u ON i.evaluator.id = u.id AND u.role = 'TEACHER' " +
            "JOIN User s ON s.interviewStudent.idInterview = i.idInterview AND s.role = 'STUDENT'" )
    List<InterviewDTO> findAllInterviewsWithEvaluatorAndStudentName();

}
