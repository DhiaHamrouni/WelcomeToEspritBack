package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.McqServiceInterface;
import com.example.welcometoesprit.ServiceInterface.QuestionServiceInterface;
import com.example.welcometoesprit.entities.MCQ;
import com.example.welcometoesprit.entities.Question;
import com.example.welcometoesprit.repository.McqRepository;
import com.example.welcometoesprit.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class QuestionServiceImp  extends BaseServiceImp<Question,Integer> implements QuestionServiceInterface {

    @Autowired
    QuestionRepository questionRepository ;


}
