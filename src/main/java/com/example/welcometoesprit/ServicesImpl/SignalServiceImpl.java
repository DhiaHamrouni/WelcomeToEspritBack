package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.MailingServiceInterface;
import com.example.welcometoesprit.ServiceInterface.SignalServiceSignal;
import com.example.welcometoesprit.entities.Publication;
import com.example.welcometoesprit.entities.SignalPost;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.repository.PublicationRepository;
import com.example.welcometoesprit.repository.SignalRepository;
import com.example.welcometoesprit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignalServiceImpl implements SignalServiceSignal {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PublicationRepository publicationRepository;
    @Autowired
    SignalRepository signalRepository;

    @Autowired
    MailingServiceInterface mailingServiceInterface;

    public String AddSignalToPost(SignalPost signalPost, Integer IdUser, Integer IdPub) {
        User u = userRepository.findById(IdUser).get();
        Publication pub = publicationRepository.findById(IdPub).get();
        System.out.println(pub.getSignalPosts().size());
        if (pub.getSignalPosts().size() > 2) {
            publicationRepository.deleteById(IdPub);
            mailingServiceInterface.sendEmail(pub.getPublierPar().getEmail(),"Supprission de post" ,"Votre Publication a Etait Supprimer");
            return "Cette Post a etait supprimer a cause des plusiers signal";
        } else {

            signalPost.setPublication(pub);
            signalPost.setSignalPar(u);
            signalRepository.save(signalPost);
            return "Post Signaler avec suuccse";


        }


    }


}