package com.example.welcometoesprit.ServicesImpl;


import com.example.welcometoesprit.entities.ConfirmationToken;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.repository.ConfirmationTokenRepository;
import com.example.welcometoesprit.repository.UserRepository;
import com.example.welcometoesprit.token.Token;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {
    @Autowired

    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private UserRepository userRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
    public void expiredAt(ConfirmationToken token){
        User user=token.getAppUser();
        if (token.getExpiresAt().isAfter(token.getCreatedAt().plusMinutes(15))) {
            userRepository.delete(user);
        }
    }

}