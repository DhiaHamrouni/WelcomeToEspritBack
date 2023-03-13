package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.InterviewServiceImp;
import com.example.welcometoesprit.entities.Interview;
import com.example.welcometoesprit.entities.Role;
import com.example.welcometoesprit.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/interview")
@CrossOrigin("*")
public class InterviewController extends BaseController<Interview,Integer>{

    @Autowired
    InterviewServiceImp interviewServiceImp;

    @PutMapping("/assign-evaluator/{interviewId}/{evaluatorId}")
    public ResponseEntity<String> assignEvaluator(@PathVariable("interviewId") Integer interviewId,@PathVariable("evaluatorId") Integer evaluatorId ) throws Exception {
       interviewServiceImp.assignInterviewToEvaluator(interviewId, evaluatorId);
        return ResponseEntity.ok("Evaluator assigned successfully");
    }

    @PutMapping("/assign-classroom/{interviewId}/{ClassroomId}")
    public ResponseEntity<String> assignClassroom(@PathVariable("interviewId") Integer interviewId,@PathVariable("ClassroomId") Integer ClassroomId) throws Exception {
        interviewServiceImp.assignClassroomToInterview(interviewId, ClassroomId);
        return ResponseEntity.ok("Classroom assigned successfully");
    }

    @PutMapping("/assign-student/{interviewId}/{StudentId}")
    public ResponseEntity<String> assignInterviewToStudent(@PathVariable("interviewId") Integer interviewId,@PathVariable("StudentId") Integer StudentId) throws Exception {
        interviewServiceImp.assignInterviewToStudent(interviewId, StudentId);
        return ResponseEntity.ok("Student assigned successfully");
    }

    @PutMapping("/TotalScore/{interviewId}")
    public ResponseEntity<String> ScoreInterview(@PathVariable("interviewId") Integer interviewId) throws Exception {
        Integer a =interviewServiceImp.ScoreInterview(interviewId);
        return ResponseEntity.ok("Total Score is : "+ a);

    }

    @PutMapping("/SetInterviewScore/{interviewId}/{score}")
    public ResponseEntity<String> SetScoreInterview(@PathVariable("interviewId") Integer interviewId,@PathVariable("score") Integer score) throws Exception {
        Integer a =interviewServiceImp.SetInterviewScore(interviewId, score);
        return ResponseEntity.ok(" Interview Score is : "+ a);

    }





    @GetMapping("/sendMailToStudent/{idUser}")
    public String sendMailInterviewDetails(@PathVariable Integer idUser){
        interviewServiceImp.sendInterviewDetails(idUser);
        return "Interview email sent!";

    }


}
