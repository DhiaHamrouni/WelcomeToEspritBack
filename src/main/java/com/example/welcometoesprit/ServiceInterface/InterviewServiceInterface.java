package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.Interview;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface InterviewServiceInterface extends BaseServiceInterface<Interview,Integer>{

    public void sendInterviewDetails(Integer idUser) ;
    public String getEmailContent(String userName, LocalDate interviewDate, String interviewTime, String interviewClass, String bloc) ;


}
