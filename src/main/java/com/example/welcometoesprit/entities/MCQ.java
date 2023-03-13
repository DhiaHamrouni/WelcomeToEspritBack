package com.example.welcometoesprit.entities;

import java.io.Serializable;
import java.util.List;
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
@Table( name = "MCQ")

public class MCQ  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMcq;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "mcq")
    private List<Question> questions;

    private int McqScore;

    @OneToOne (mappedBy="mcqInterview")
    private Interview interview;
}
