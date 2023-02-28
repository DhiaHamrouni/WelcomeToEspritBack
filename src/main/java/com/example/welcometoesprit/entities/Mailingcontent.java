package com.example.welcometoesprit.entities;

import lombok.*;

import jakarta.persistence.*;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Mailingcontent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String toEmail;
    private String Subject;
    private String body;
}
