package com.example.welcometoesprit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

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

    @JsonIgnore
    @ManyToMany(cascade= CascadeType.ALL, mappedBy = "classroomSet")
    List<User> listOfStudent;

    @ManyToOne
    Bloc bloc;

    @OneToMany(mappedBy = "classroom")
    private Set<Interview> interviews;

}
