package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.ClassroomServiceInterface;
import com.example.welcometoesprit.entities.Bloc;
import com.example.welcometoesprit.entities.Classroom;
import com.example.welcometoesprit.repository.BlocRepository;
import com.example.welcometoesprit.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomServiceImp extends BaseServiceImp<Classroom,Integer> implements ClassroomServiceInterface {
    @Autowired
    ClassroomRepository classroomRepository;
    @Autowired
    private BlocRepository blocRepository;


    public void assignBlocToClassroom(Integer idClassroom,Integer idBloc){
        Classroom classroom = classroomRepository.findById(idClassroom).get();
        Bloc bloc = blocRepository.findById(idBloc).get();
        classroom.setBloc(bloc);
        classroomRepository.save(classroom);

    }
}
