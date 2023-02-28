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
@Table( name = "Bloc")
public class Bloc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBloc")
    private Integer idBloc; // Clé primaire
    private String nomBloc;

    @ManyToOne
    University university2;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "bloc")
    List<Classroom> listOfClassroom;
}
