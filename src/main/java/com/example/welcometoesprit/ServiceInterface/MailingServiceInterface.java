package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.Mailingcontent;
import com.example.welcometoesprit.entities.User;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public interface MailingServiceInterface extends BaseServiceInterface<Mailingcontent,Integer>{

    public void sendEmail(String toEmail,
                          String Subject,
                          String body);

    public List<Mailingcontent> gethistory(int  a);

    public void sendMailStudentConfirmation(User user);
    public void sendEmaill(SimpleMailMessage message);
    void sendEmailTemplate (String to ,String email);


}
