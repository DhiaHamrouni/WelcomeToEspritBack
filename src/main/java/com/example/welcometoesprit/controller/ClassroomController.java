package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.ClassroomServiceImp;
import com.example.welcometoesprit.entities.Classroom;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classroom")
@CrossOrigin("*")
public class ClassroomController extends BaseController<Classroom,Integer>{

    @Autowired
    ClassroomServiceImp classroomServiceImp;

    @PutMapping("assignBlocToClassroom/{idClass}/{idBloc}")
    public void assignBlocToClassroom(@PathVariable Integer idClass, @PathVariable Integer idBloc){
        classroomServiceImp.assignBlocToClassroom(idClass,idBloc);
    }
}
