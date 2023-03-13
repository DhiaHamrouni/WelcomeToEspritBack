package com.example.welcometoesprit.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "Interview")

public class Interview  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInterview;
    private Date dateInterview ;
    private Integer heureInterview;
    private Integer TotalScore;
    private Integer QcmScore;
    private Integer InterviewScore;
    private String Deliberation; // not sure if string

    @OneToOne(mappedBy = "interviewStudent",cascade = CascadeType.ALL)
    private User student;

    @ManyToOne
    private User evaluator;

    @ManyToOne
    private Classroom classroom;

    public Interview(Date dateInterview,Integer heureInterview, User student, User evaluator) {
        this.dateInterview = dateInterview;
        this.heureInterview=heureInterview;
        this.student = student;
        this.evaluator = evaluator;
    }
}
