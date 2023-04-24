package com.example.welcometoesprit.entities;

import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Mailingcontent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String toEmail;
    private String Subject;
    private String body;
}
