package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.InterviewServiceInterface;
import com.example.welcometoesprit.entities.*;
import com.example.welcometoesprit.repository.ClassroomRepository;
import com.example.welcometoesprit.repository.InterviewRepository;
import com.example.welcometoesprit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InterviewServiceImp extends BaseServiceImp<Interview,Integer> implements InterviewServiceInterface {

    @Autowired
    UserRepository userRepository;
    @Autowired
    InterviewRepository interviewRepository;
    @Autowired
    ClassroomRepository classroomRepository;



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



        if (student.getRole() != Role.USER) {
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


}
