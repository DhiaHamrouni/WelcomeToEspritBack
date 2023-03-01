package com.example.welcometoesprit.auth;


import com.example.welcometoesprit.config.JwtService;
import com.example.welcometoesprit.token.Token;
import com.example.welcometoesprit.token.TokenRepository;
import com.example.welcometoesprit.token.TokenType;
import com.example.welcometoesprit.entities.Role;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final List<String> stringList= Arrays.asList("User Exists Already");

  public AuthenticationResponse register(RegisterRequest request) {

    User user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.STUDENT)
        .build();
    if (repository.findByEmail(user.getEmail()).isEmpty()){
      var savedUser = repository.save(user);
      var jwtToken = jwtService.generateToken(user);
      saveUserToken(savedUser, jwtToken);
      return AuthenticationResponse.builder()
              .token(jwtToken)
              .build();
    }
    System.out.println(user.getId());
    return AuthenticationResponse.builder().errors(stringList).build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}
