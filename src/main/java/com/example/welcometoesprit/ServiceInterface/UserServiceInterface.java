package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.dto.TeacherDto;
import com.example.welcometoesprit.dto.UserDTO;
import com.example.welcometoesprit.entities.NiveauActuel;
import com.example.welcometoesprit.entities.NiveauSuivant;
import com.example.welcometoesprit.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface UserServiceInterface extends BaseServiceInterface<User,Integer>{

    public void addFileAndAssignToStudent(MultipartFile file, Integer idUser) throws IOException;

    public NiveauSuivant levelSuggestion(Integer idUser);

    //public NiveauSuivant showNextLevel(Integer idUser);

    public void refuseNextLevel(Integer idUser,User user);

    public NiveauActuel addNiveauActuel(Integer idUser,User user);

    public List<User> getStudents();

    public String addInterviewAndAssignToStudent(Integer idStudent, Date dateInterview,Integer heureInterview);
    public List<UserDTO> findStudentsByFirstName(UserDTO userDto);
    public List<TeacherDto> findTeachersByFirstNameAndLastName(String firstName, String lastName);
    public void assignInterviewToTeacher(Integer idStudent);
    public User updateUser(Integer userId, User updatedUser);





    }
