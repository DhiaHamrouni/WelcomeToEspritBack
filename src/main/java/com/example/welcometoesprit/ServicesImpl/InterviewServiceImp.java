package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.InterviewServiceInterface;
import com.example.welcometoesprit.entities.Interview;
import com.example.welcometoesprit.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterviewServiceImp extends BaseServiceImp<Interview,Integer> implements InterviewServiceInterface {

    @Autowired
    InterviewRepository interviewRepository;
}
