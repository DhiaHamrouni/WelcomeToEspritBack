package com.example.welcometoesprit.dto;

import com.example.welcometoesprit.entities.NiveauSuivant;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDTO {
    private String firstName;
    private String lastName;
    private NiveauSuivant niveauSuivant;

    public UserDTO(String firstName, String lastName, NiveauSuivant niveauSuivant) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.niveauSuivant = niveauSuivant;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNiveauSuivant(NiveauSuivant niveauSuivant) {
        this.niveauSuivant = niveauSuivant;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public NiveauSuivant getNiveauSuivant() {
        return niveauSuivant;
    }
}
