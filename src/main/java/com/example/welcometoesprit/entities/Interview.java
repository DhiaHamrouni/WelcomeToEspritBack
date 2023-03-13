package com.example.welcometoesprit.entities;


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
@Table( name = "Interview")

public class Interview  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInterview;

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date scheduledTime ;

    private Integer TotalScore;
    private Integer InterviewScore;
    private String Deliberation; // not sure if string

    @OneToOne
    private User student;

    @ManyToOne(cascade = CascadeType.ALL)
    private User evaluator;

    @ManyToOne(cascade = CascadeType.ALL)
    private Classroom classroomInterview;

    @OneToOne
    private MCQ mcqInterview;

}
