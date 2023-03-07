package com.example.welcometoesprit.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
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
    private natija result;
    @OneToOne
    CondidatOffre condidatOffreResult;
    @OneToMany(cascade = CascadeType.ALL)
    List<Jury> juries;
}
