package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.Mailingcontent;
import com.example.welcometoesprit.entities.NiveauSuivant;
import com.example.welcometoesprit.entities.User;
import com.lowagie.text.Document;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public interface MailingServiceInterface extends BaseServiceInterface<Mailingcontent,Integer>{

    public void sendEmail(String toEmail,
                          String Subject,
                          String body);

    public List<Mailingcontent> gethistory(int  a);

    public void sendMailToAdministrationLevel(Integer idUser, NiveauSuivant niveauSuivant);
    public void sendEmaill(SimpleMailMessage message);
    void sendEmailTemplate (String to ,String email);

    public void sendMailInterviewDetailsToStudent(Integer idStudent);
    public void sendEmailpdf(String to, InputStreamSource document);

}
