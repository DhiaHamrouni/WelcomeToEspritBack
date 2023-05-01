package com.example.welcometoesprit.dto;

import java.time.LocalTime;
import java.util.Date;

public class InterviewDTO {
    private Integer idInterview;
    private String evaluatorName;
    private String evaluatorLastName;
    private String studentName;
    private String studentLastName;
    private Date interviewDate;
    private LocalTime interviewHeure;

    public InterviewDTO(Integer id, String evaluatorName, String evaluatorLastName, String studentName, String studentLastName,Date interviewDate,LocalTime interviewHeure) {
        this.idInterview = id;
        this.evaluatorName = evaluatorName;
        this.evaluatorLastName = evaluatorLastName;
        this.studentName = studentName;
        this.studentLastName = studentLastName;
        this.interviewDate=interviewDate;
        this.interviewHeure=interviewHeure;
    }

    public void setIdInterview(Integer idInterview) {
        this.idInterview = idInterview;
    }

    public void setEvaluatorName(String evaluatorName) {
        this.evaluatorName = evaluatorName;
    }

    public void setEvaluatorLastName(String evaluatorLastName) {
        this.evaluatorLastName = evaluatorLastName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public void setInterviewHeure(LocalTime interviewHeure) {
        this.interviewHeure = interviewHeure;
    }

    public Integer getIdInterview() {
        return idInterview;
    }

    public String getEvaluatorName() {
        return evaluatorName;
    }

    public String getEvaluatorLastName() {
        return evaluatorLastName;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public LocalTime getInterviewHeure() {
        return interviewHeure;
    }
}
