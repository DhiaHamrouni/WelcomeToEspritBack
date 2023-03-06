package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.SmsServiceInterface;
import com.example.welcometoesprit.entities.Interview;
import com.example.welcometoesprit.entities.Role;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.repository.InterviewRepository;
import com.example.welcometoesprit.repository.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class SmsServiceImp implements SmsServiceInterface {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${TWILIO_ACCOUNT_SID}")
    String ACCOUNT_SID;

    @Value("${TWILIO_AUTH_TOKEN}")
    String AUTH_TOKEN;

    @Value("${TWILIO_OUTGOING_SMS_NUMBER}")
    String OUTGOING_SMS_NUMBER;



    @PostConstruct
    private void setup(){
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
    }

    @Override
    public String sendSms(String smsNumber, String smsMessage) {

        Message message = Message.creator(
                new PhoneNumber(smsNumber),
                new PhoneNumber(OUTGOING_SMS_NUMBER),
                smsMessage).create();

        return message.getStatus().toString();
    }

    /*@Scheduled(fixedRate = 86400000) // execute once per day
    @Override
    public void sendSmsReminders() {
        List<Interview> upcomingInterviews = interviewRepository.findByDateInterviewIsBetween(
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(25)
        );
        for (Interview interview : upcomingInterviews) {
            User user = userRepository.findById(interview.getStudent().getId()).orElse(null);

            if (user.getRole() == Role.STUDENT) {
                String smsMessage = "Reminder: Your interview is scheduled for " + interview.getDateInterview() +
                        " in classroom " + interview.getClassroom().getNumero() + "at the "+interview.getClassroom().getEtage()
                        +" floor , which is in the "+ interview.getClassroom().getBloc().getNomBloc()+" bloc .";
                sendSms(user.getNumTel(), smsMessage);
            }
        }
    }*/
}
