package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.InterviewServiceImp;
import com.example.welcometoesprit.ServicesImpl.McqServiceImp;
import com.example.welcometoesprit.entities.Interview;
import com.example.welcometoesprit.entities.MCQ;
import com.example.welcometoesprit.entities.Question;
import com.example.welcometoesprit.repository.McqRepository;
import com.example.welcometoesprit.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.welcometoesprit.entities.Category.CULTURE;
import static com.example.welcometoesprit.entities.Category.LANGUAGE;

@RestController
@RequestMapping("/mcq")
@CrossOrigin("*")

public class McqController  extends BaseController<MCQ,Integer> {

    @Autowired
    McqServiceImp mcqServiceImp;
    @Autowired

    QuestionRepository questionRepository;



    @GetMapping("/showCultureQs")
    public List<Question>  show() throws Exception {

        return questionRepository.findByCategory(CULTURE);


    }


    @PostMapping("/generateQSAndAssignToStudent/{id-student}")
    public ResponseEntity<String> generateQsAndAssignToStudent(@PathVariable("id-student") Integer id) throws Exception {

        mcqServiceImp.GenerateMcqAndAssignToUser(id);
            return ResponseEntity.ok("MCQ generated");


        }

    // calculate score and assign to user .
    @PutMapping("/{id}/takeMCQ")
    public int calculateScore(@RequestBody List<Integer> answers,@PathVariable("id") Integer id) throws Exception {
        return mcqServiceImp.TakeMcqAndCalculateScore(id,answers);
    }

    @GetMapping("/showLanguageQs")
    public List<Question>  showL() throws Exception {

        return questionRepository.findByCategory(LANGUAGE);


    }
}
