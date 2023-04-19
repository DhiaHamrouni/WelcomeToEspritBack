package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.InterviewServiceInterface;

import com.example.welcometoesprit.dto.InterviewDTO;
import com.example.welcometoesprit.entities.*;
import com.example.welcometoesprit.repository.*;

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

import java.util.Date;
import java.util.List;

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
    UserRepository userRepository;
    @Autowired
    InterviewRepository interviewRepository;
    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private BlocRepository blocRepository;


    public void assignInterviewToEvaluator(Integer interviewId, Integer evaluatorId) throws Exception {
        
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new Exception("Interview not found"));

//        User evaluator = userRepository.findById(evaluatorId)
//                .orElse(null);
        User evaluator = userRepository.getReferenceById(evaluatorId);

        if (evaluator.getRole() != Role.TEACHER) {
            throw new Exception("Only evaluators can be assigned to interviews");
        }

        if (!isEvaluatorAvailable(evaluator, interview.getScheduledTime())) {
            throw new Exception("Evaluator is not available at the scheduled time");
        }

        interview.setEvaluator(evaluator);
        interviewRepository.save(interview);
    }



    public boolean isEvaluatorAvailable(User evaluator, Date scheduledTime) {

//        // Get all the interviews scheduled at the given time
//        List<Interview> interviewsAtScheduledTime = interviewRepository.findByScheduledTime(scheduledTime);
//
//        // Check if the evaluator is already assigned to any interview at the scheduled time
//        if (interviewsAtScheduledTime.stream().noneMatch(i -> i.getEvaluator() == evaluator)) {
//            // Check if the evaluator has any other interviews scheduled within the next 30 minutes of the scheduled time
//            LocalDateTime nextThirtyMinutes = scheduledTime.plusMinutes(30);
//            List<Interview> nextThirtyMinutesInterviews = interviewRepository.findByEvaluatorAndScheduledTimeBetween(
//                    evaluator, scheduledTime, nextThirtyMinutes);
//            return nextThirtyMinutesInterviews.isEmpty();
//        }
        List<Interview> interviewsAtScheduledTime = interviewRepository.findByEvaluatorAndScheduledTimeBetween(
                evaluator,
                new Date(scheduledTime.getTime() - 1800000), // subtract 30 minutes from start time
                new Date(scheduledTime.getTime() + 1800000) // add 30 minutes to start time
        );
        return interviewsAtScheduledTime.isEmpty();
    }

    public void assignClassroomToInterview( Integer interviewId , Integer classroomId) throws Exception {
        // Check if the classroom is available at the scheduled time
       Interview interview = interviewRepository.findById(interviewId).orElseThrow(() -> new Exception("Interview not found"));
       Classroom classroom = classroomRepository.findById(classroomId).orElseThrow(() -> new Exception("Classroom not found"));
        if (isClassroomAvailable(classroom, interview.getScheduledTime())) {
            // Assign the classroom to the interview
            interview.setClassroomInterview(classroom);
            interviewRepository.save(interview);
        } else {
            throw new RuntimeException("Classroom not available at the scheduled time");
        }
    }

    // Function to check if the classroom is available at the scheduled time
    public boolean isClassroomAvailable(Classroom classroom, Date scheduledTime) {
        // Get all the interviews scheduled at the given time
//        List<Interview> interviewsAtScheduledTime = interviewRepository.findByScheduledTime(scheduledTime);
//
//        // Check if the classroom is already assigned to any interview at the scheduled time
//        if (interviewsAtScheduledTime.stream().noneMatch(i -> i.getClassroomInterview() == classroom)) {
//            // Check if the classroom has any other interviews scheduled within the next 30 minutes of the scheduled time
//            LocalDateTime nextThirtyMinutes = scheduledTime.plusMinutes(30);
//            List<Interview> nextThirtyMinutesInterviews = interviewRepository.findByClassroomInterviewAndScheduledTimeBetween(
//                    classroom, scheduledTime, nextThirtyMinutes);
//            return nextThirtyMinutesInterviews.isEmpty();
//        }
//
//        return false;

        List<Interview> ClassroomsAtScheduledTime = interviewRepository.findByClassroomInterviewAndScheduledTimeBetween(
                classroom,
                new Date(scheduledTime.getTime() - 1800000), // subtract 30 minutes from start time
                new Date(scheduledTime.getTime() + 1800000) // add 30 minutes to start time
        );
        // 9 , 9:31
        return ClassroomsAtScheduledTime.isEmpty();

    }

    public void assignInterviewToStudent(Integer interviewId, Integer studentId) throws Exception {
        User student = userRepository.getReferenceById(studentId);
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new Exception("Interview not found"));



        if (student.getRole() != Role.STUDENT) {
            throw new Exception("Only Students can be assigned to interviews");
        }
        interview.setStudent(student);
        interviewRepository.save(interview);

    }

    public Integer ScoreInterview(Integer interviewId) throws Exception {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new Exception("Interview not found"));

        interview.setTotalScore(interview.getInterviewScore()+interview.getMcqInterview().getMcqScore());
        interviewRepository.save(interview);

        return interview.getTotalScore();

    }

    public Integer SetInterviewScore(Integer interviewId, Integer score) throws Exception {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new Exception("Interview not found"));

        interview.setInterviewScore(score);
        interviewRepository.save(interview);


        return interview.getInterviewScore();
    }


    @Override
    public void sendInterviewDetails(Integer idUser) {
        User user = userRepository.findById(idUser).get();
        if ((user.getRole() == Role.STUDENT)&&(user.getInterviewStudent().getIdInterview()!=null)) {
            Interview interview = user.getInterviewStudent();
//            Date input = interview.getDateInterview();
//            LocalDate date = LocalDate.ofInstant(input.toInstant(), ZoneId.systemDefault());

            Date date = new Date();

            String interviewTime = String.valueOf(interview.getHeureInterview());
            Classroom classroom1 = classroomRepository.findById(1).get();
            Integer classroom = (classroom1.getNumero()+classroom1.getEtage()*100);
            Bloc bloc1=blocRepository.findById(1).get();
            String bloc = bloc1.getNomBloc();
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
    public String getEmailContent(String userName, Date interviewDate, String interviewTime,String interviewClass,String bloc) {
        String htmlTemplate = "";
        try {
            Resource resource = new ClassPathResource("templates/mailInterview.html");
            htmlTemplate = new String(resource.getInputStream().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String htmlContent = htmlTemplate
                .replace("[student name]", userName)
                .replace("[interview date]", interviewDate.toString() )
                .replace("[interview time]", interviewTime)
                .replace("[interview location]", interviewClass)
                .replace("[interview bloc]",bloc);
        return htmlContent;
    }

    @Override
    public List<InterviewDTO> getAllInterviewsWithEvaluatorAndStudentName() {
        return interviewRepository.findAllInterviewsWithEvaluatorAndStudentName();
    }


}
