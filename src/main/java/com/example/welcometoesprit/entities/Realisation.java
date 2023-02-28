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
@Table( name = "Realisation")
public class Realisation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRealisation")
    private Integer idRealisation; // Clé primaire
    private String nomEquipe;

    @OneToOne
    private FileInfo video;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "realisation")
    List<User> groupe;

}
