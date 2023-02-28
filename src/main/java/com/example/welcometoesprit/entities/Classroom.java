package com.example.welcometoesprit.entities;

import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "Classroom")
public class Classroom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClassroom")
    private Integer idClassroom; // Clé primaire
    private Integer numero;
    private Integer etage;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "classroom")
    List<User> listOfStudent;

    @ManyToOne
    Bloc bloc;
}
