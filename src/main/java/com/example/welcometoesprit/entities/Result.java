package com.example.welcometoesprit.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "results")
public class Result implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResult")
    private Integer idResult; // Clé primaire
    private Integer Criteria1;
    private Integer Criteria2;
    private Integer Criteria3;
    private natija result;

    @OneToOne
    CondidatOffre condidatOffreResult;
    @OneToOne
    Jury jury;

}
