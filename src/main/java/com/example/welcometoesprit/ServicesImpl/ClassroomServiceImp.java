package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.ClassroomServiceInterface;
import com.example.welcometoesprit.entities.Classroom;
import com.example.welcometoesprit.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomServiceImp extends BaseServiceImp<Classroom,Integer> implements ClassroomServiceInterface {
    @Autowired
    ClassroomRepository classroomRepository;

}
