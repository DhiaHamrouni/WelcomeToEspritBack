package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.NiveauActuel;
import com.example.welcometoesprit.entities.NiveauSuivant;
import com.example.welcometoesprit.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserServiceInterface extends BaseServiceInterface<User,Integer>{

    public void addFileAndAssignToStudent(MultipartFile file, Integer idUser) throws IOException;

    public NiveauSuivant levelSuggestion(Integer idUser);

    //public NiveauSuivant showNextLevel(Integer idUser);

    public void refuseNextLevel(Integer idUser,User user);

    public NiveauActuel addNiveauActuel(Integer idUser,User user);
}
