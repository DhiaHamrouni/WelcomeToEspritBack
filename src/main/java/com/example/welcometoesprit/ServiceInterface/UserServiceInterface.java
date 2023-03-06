package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.NiveauActuel;
import com.example.welcometoesprit.entities.NiveauSuivant;
import com.example.welcometoesprit.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface UserServiceInterface extends BaseServiceInterface<User,Integer>{

    public void addFileAndAssignToStudent(MultipartFile file, Integer idUser) throws IOException;

    public NiveauSuivant levelSuggestion(Integer idUser);

    //public NiveauSuivant showNextLevel(Integer idUser);

    public void refuseNextLevel(Integer idUser,User user);

    public NiveauActuel addNiveauActuel(Integer idUser,User user);

    public Integer assignStudentToEvaluator(Integer idStudent,LocalDateTime dateInterview); //return type id Evaluator

    public Integer assignStudentToClassroom(Integer idStudent,Integer idEvaluator); // return type id Classroom where the interview will take place

    public String assignStudentToInterview(Integer idStudent,LocalDateTime dateInterview); //return "check your email for further details about your interview"

    public List<User> getStudents();




}
