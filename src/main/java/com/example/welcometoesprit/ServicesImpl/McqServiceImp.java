package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.InterviewServiceInterface;
import com.example.welcometoesprit.ServiceInterface.McqServiceInterface;
import com.example.welcometoesprit.entities.*;
import com.example.welcometoesprit.repository.InterviewRepository;
import com.example.welcometoesprit.repository.McqRepository;
import com.example.welcometoesprit.repository.QuestionRepository;
import com.example.welcometoesprit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.example.welcometoesprit.entities.Category.CULTURE;
import static com.example.welcometoesprit.entities.Category.LANGUAGE;

@Service
public class McqServiceImp  extends BaseServiceImp<MCQ,Integer> implements McqServiceInterface {
//
//    @Autowired
//    McqRepository mcqRepository;
//    @Autowired
//    QuestionRepository questionRepository;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    InterviewRepository interviewRepository;
//
////    public List<Question> generateCultureQs() {
////        // get general culture questions
////        List<Question> Culturequestions = questionRepository.findByCategory(CULTURE);
////        Collections.shuffle(Culturequestions);
////        List<Question> selectedQuestionsCulture = Culturequestions.subList(0, 10);
////
////
////    }
//
//    public List<Question> generateQs(Category category)
//    {
//        List<Question> questions = questionRepository.findByCategory(category);
//        Collections.shuffle(questions);
//        return questions.subList(0, 2);
//    }
//
//
//
//    public void assignQsToMcq(MCQ mcq,List<Question> questions) throws Exception {
//        mcq.setQuestions(questions);
//        mcqRepository.save(mcq);
//
//    }
//
//    // question -> mcq -> interview -> student
////    public void assignMcqToStudent(Integer idMcq,Integer idStudent) throws Exception {
////        MCQ mcq = mcqRepository.findById(idMcq).orElseThrow(() -> new Exception("MCQ not found"));
////        User student = userRepository.findById(idStudent).orElseThrow(() -> new Exception("MCQ not found"));
////        Interview interview = student.getInterviewStudent();
////        interview.setMcqInterview(mcq);
////        interviewRepository.save(interview);
////
////    }
//
//    public void GenerateMcqAndAssignToUser(Integer idStudent) throws Exception {
//
//        MCQ mcq = new MCQ();
//        // generate qs
//
//        List<Question> questions = generateQs(LANGUAGE);
//        questions.addAll(generateQs(CULTURE));
//        questionRepository.saveAll(questions);
//
//        // assign questions to mcq
//        mcq.setQuestions(questions);
//        mcqRepository.save(mcq);
//
//        // set to student through interview
//        User student = userRepository.findById(idStudent).orElseThrow(() -> new Exception("Student not found"));
//        Interview interview = interviewRepository.getReferenceById(student.getInterviewStudent().getIdInterview());
////        Interview interview = student.getInterviewStudent();
//        interview.setMcqInterview(mcq);
//        interviewRepository.save(interview);
//
//    };
//
//    public int TakeMcqAndCalculateScore(Integer idUser, List<Integer> answers) throws Exception {
//
//        User user= userRepository.findById(idUser).orElseThrow(() -> new Exception("User not found"));
//        MCQ mcq =user.getInterviewStudent().getMcqInterview();
//        List<Question> questions =mcq.getQuestions();
//        int score = 0;
//        for (int i = 0; i < questions.size(); i++) {
//            if (questions.get(i).getCorrectanswer() == answers.get(i)) {
//                score++;
//            }
//        }
//        user.getInterviewStudent().getMcqInterview().setMcqScore(score);
//        userRepository.save(user);
//        return score;
//    }

}
