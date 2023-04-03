package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.Interview;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface InterviewServiceInterface extends BaseServiceInterface<Interview,Integer>{


    public void assignInterviewToEvaluator(Integer interviewId, Integer evaluatorId) throws Exception;
    public void assignClassroomToInterview(Integer interviewId, Integer classroomId)throws Exception;
    public void assignInterviewToStudent(Integer interviewId, Integer studentId) throws Exception;
    public Integer ScoreInterview(Integer interviewId) throws Exception;
    public Integer SetInterviewScore(Integer interviewId, Integer score) throws Exception;

    public void sendInterviewDetails(Integer idUser) ;
    public String getEmailContent(String userName, Date interviewDate, String interviewTime, String interviewClass, String bloc) ;


}
