package com.example.welcometoesprit.auth;

import com.example.welcometoesprit.entities.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @NotBlank(message = "First name is required")
  private String firstname;
  @NotBlank(message = "Last name is required")
  private String lastname;
  @Email(message = "Invalid email address")
  @NotBlank(message = "Email is required")
  private String email;
  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  private String cin;
  @NotBlank(message = "Username is required")
  private Nationality nationality;
  private Sexe sexe;
  private TypeCours typeCours;
  private NiveauActuel niveauActuel;
  private String password;
  private LocalDateTime registrationDate= LocalDateTime.now();
  private Role role=Role.STUDENT;

}
