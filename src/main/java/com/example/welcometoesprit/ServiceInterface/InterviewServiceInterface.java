package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.Interview;
import com.example.welcometoesprit.entities.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface InterviewServiceInterface extends BaseServiceInterface<Interview,Integer>{

    public void sendInterviewDetails(Integer idUser) ;
    public String getEmailContent(String userName, LocalDate interviewDate, String interviewTime, String interviewClass, String bloc) ;
    public String assignInterviewToStudent(Integer idStudent, LocalDateTime dateInterview);


}
