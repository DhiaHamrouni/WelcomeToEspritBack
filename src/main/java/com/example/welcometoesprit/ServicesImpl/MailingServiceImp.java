package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.MailingServiceInterface;
import com.example.welcometoesprit.entities.Mailingcontent;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.repository.MailingRepository;
import com.example.welcometoesprit.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j

public class MailingServiceImp extends BaseServiceImp<Mailingcontent, Integer> implements MailingServiceInterface {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    MailingRepository mailingRepository;
    @Autowired
    UserRepository appUserRepository;

    public void sendEmail(String toEmail,
                          String Subject,
                          String body
    ){

        log.info("sending mail to {} ,subject is :{}",toEmail,Subject);
        SimpleMailMessage message =  new SimpleMailMessage();
        message.setFrom("mahdi.fersi@esprit.tn");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(Subject);

        javaMailSender.send(message);

        Mailingcontent mailingcontent= new Mailingcontent();
        mailingcontent.setBody(body);
        mailingcontent.setSubject(Subject);
        mailingcontent.setToEmail(toEmail);
        mailingRepository.save(mailingcontent);
    }

    @Override
    public  List<Mailingcontent> gethistory(int a) {
        User u = appUserRepository.findById(a).orElse(null);

        List<Mailingcontent> mailingcontents = mailingRepository.getAllByToEmail(u.getEmail());
        return mailingcontents;

    }

    @Override
    public void sendMailStudentConfirmation(User user) {
        String toEmail= user.getEmail();
        String Subject = "Confirmation Mail";
        String body = " Welcome to Esprit , " +
                "this is an automatic confirmation mail for your account registration ";
        sendEmail(toEmail,Subject,body);
    }

    @Override
    public void sendEmaill(SimpleMailMessage message) {


        javaMailSender.send(message);

        Mailingcontent mailingcontent= new Mailingcontent();
        mailingcontent.setBody(message.getText());
        mailingcontent.setSubject(message.getSubject());
        mailingcontent.setToEmail(message.getReplyTo());
        mailingRepository.save(mailingcontent);
    }

    @Override
    @Async
    public void sendEmailTemplate(String to, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");
            message.setTo(to);
            message.setSubject(" Registration Confirmation mail ");
            message.setText(email, true);
            message.setFrom("mahdi.fersi@esprit.tn");
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException e)  {
            throw new IllegalStateException("failed to send email");
        }
    }
}
