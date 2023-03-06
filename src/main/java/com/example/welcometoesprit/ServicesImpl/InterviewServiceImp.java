package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.InterviewServiceInterface;
import com.example.welcometoesprit.entities.Interview;
import com.example.welcometoesprit.entities.Role;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.repository.InterviewRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class InterviewServiceImp extends BaseServiceImp<Interview,Integer> implements InterviewServiceInterface {

    @Autowired
    InterviewRepository interviewRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendInterviewDetails(User user) {
        if (user.getRole() == Role.STUDENT) {
            Interview interview = user.getInterviewStudent();
            LocalDate interviewDate = interview.getDateInterview().toLocalDate();
            String interviewTime = String.valueOf(interview.getDateInterview().getHour());
            //String interviewLocation = interview.getClassroom().getNumero().toString();
            String userEmail = user.getEmail();
            String userName = user.getFirstname();

            String emailContent = getEmailContent(userName, interviewDate, interviewTime);

            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(userEmail);
                helper.setSubject("University Interview Invitation");
                helper.setText(emailContent, true);
                message.setFrom("mahdi.fersi@esprit.tn");
                javaMailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private String getEmailContent(String userName, LocalDate interviewDate, String interviewTime) {
        String htmlTemplate = "";
        try {
            Resource resource = new ClassPathResource("templates/mailInterview.html");
            htmlTemplate = new String(resource.getInputStream().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String htmlContent = htmlTemplate
                .replace("[user name]", userName)
                .replace("[interview date and time]", interviewDate.toString() + " at " + interviewTime);
                //.replace("[interview location]", interviewLocation);
        return htmlContent;
    }


}
