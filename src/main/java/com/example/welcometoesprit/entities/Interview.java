package com.example.welcometoesprit.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "Interview")

public class Interview  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInterview;

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date scheduledTime ;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateInterview ;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime heureInterview;
    private Integer TotalScore;
    private Integer InterviewScore;
    private String Deliberation; // not sure if string


    @OneToOne(mappedBy = "interviewStudent")
    @JsonIgnore
    private User student;

    @ManyToOne
    private User evaluator;

    @ManyToOne
    @JsonIgnore
    private Classroom classroomInterview;

    @OneToOne
    private MCQ mcqInterview;


    public Interview(Date dateInterview, User student, User evaluator,LocalTime heureInterview) {
        this.dateInterview = dateInterview;
        this.heureInterview=heureInterview;
        this.student = student;
        this.evaluator = evaluator;
    }



}
