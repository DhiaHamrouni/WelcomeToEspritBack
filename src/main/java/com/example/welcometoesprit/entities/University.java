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
@Table( name = "University")
public class University implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUniversity")
    private Integer idUniversity; // Clé primaire
    private  String nomUniversity;

    @OneToMany(mappedBy = "university",cascade = CascadeType.ALL)
    private Set<Departement> departementSet;

    @OneToMany(mappedBy = "university2",cascade = CascadeType.ALL)
    private Set<Bloc> blocs;
}
