package com.example.welcometoesprit.entities;

import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "Offre")
public class Offre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOffre")
    private Integer idOffre; // Clé primaire
    private String JuryAppreciation;
    private String Result;
    private String titre;
    private String description;
    private String Jury;


    @OneToOne
    private FileEntity Doc;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<CondidatOffre> condidatOffres;
    @ManyToOne
    Departement departement;
    }
