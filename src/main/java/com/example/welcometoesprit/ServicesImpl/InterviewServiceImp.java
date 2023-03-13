package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.InterviewServiceInterface;
import com.example.welcometoesprit.entities.Classroom;
import com.example.welcometoesprit.entities.Interview;
import com.example.welcometoesprit.entities.Role;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.repository.ClassroomRepository;
import com.example.welcometoesprit.repository.InterviewRepository;
import com.example.welcometoesprit.repository.UserRepository;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class InterviewServiceImp extends BaseServiceImp<Interview,Integer> implements InterviewServiceInterface {

    @Autowired
    InterviewRepository interviewRepository;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    public void sendInterviewDetails(Integer idUser) {
        User user = userRepository.findById(idUser).get();
        if ((user.getRole() == Role.STUDENT)&&(user.getInterviewStudent().getIdInterview()!=null)) {
            Interview interview = user.getInterviewStudent();
            Date input = interview.getDateInterview();
            LocalDate date = LocalDate.ofInstant(input.toInstant(), ZoneId.systemDefault());
            String interviewTime = String.valueOf(interview.getHeureInterview());
            Integer classroom = (interview.getClassroom().getNumero()+interview.getClassroom().getEtage()*100);
            String bloc = interview.getClassroom().getBloc().getNomBloc();
            String interviewClass = classroom.toString();
            String userEmail = user.getEmail();
            String userName = user.getFirstname();

            String emailContent = getEmailContent(userName, date,interviewTime,interviewClass,bloc);

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

    @Override
    public String getEmailContent(String userName, LocalDate interviewDate, String interviewTime,String interviewClass,String bloc) {
        String htmlTemplate = "";
        try {
            Resource resource = new ClassPathResource("templates/mailInterview.html");
            htmlTemplate = new String(resource.getInputStream().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String htmlContent = htmlTemplate
                .replace("[student name]", userName)
                .replace("[interview date]", interviewDate.toString() + " at " + interviewTime)
                .replace("[interview time]", interviewTime)
                .replace("[interview location]", interviewClass)
                .replace("[interview bloc]",bloc);
        return htmlContent;
    }




}
