package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.Interview;

public interface InterviewServiceInterface extends BaseServiceInterface<Interview,Integer>{


    public void assignInterviewToEvaluator(Integer interviewId, Integer evaluatorId) throws Exception;
    public void assignClassroomToInterview(Integer interviewId, Integer classroomId)throws Exception;
    public void assignInterviewToStudent(Integer interviewId, Integer studentId) throws Exception;
    public Integer ScoreInterview(Integer interviewId) throws Exception;
    public Integer SetInterviewScore(Integer interviewId, Integer score) throws Exception;
}
