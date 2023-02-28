package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.Mailingcontent;
import com.example.welcometoesprit.entities.User;

import java.util.List;

public interface MailingServiceInterface extends BaseServiceInterface<Mailingcontent,Integer>{

    public void sendEmail(String toEmail,
                          String Subject,
                          String body);

    public List<Mailingcontent> gethistory(int  a);

    public void sendMailStudentConfirmation(User user);

}
