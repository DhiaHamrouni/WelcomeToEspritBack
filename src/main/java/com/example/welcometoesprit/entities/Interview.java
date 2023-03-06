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
    private LocalDateTime dateInterview ;
    private Integer TotalScore;
    private Integer QcmScore;
    private Integer InterviewScore;
    private String Deliberation; // not sure if string

    @OneToOne
    private User student;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> evaluators;

    @ManyToOne
    private Classroom classroom;

}
