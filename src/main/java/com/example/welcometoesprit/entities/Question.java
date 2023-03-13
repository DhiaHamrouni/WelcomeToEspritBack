package com.example.welcometoesprit.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "Question")

public class Question  implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ElementCollection
    private List<String> choices;

    private int Correctanswer;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    private MCQ mcq;

}
