package com.example.welcometoesprit.entities;

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
@Table( name = "Publication")
public class Publication implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPublication")
    private Integer idPublication; // Clé primaire
    private String sujet ;
    private  String contenu;

    @ManyToOne
    User publierPar;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> likerPar;

    @OneToMany(cascade = CascadeType.ALL)
    List<Comment> listOfComments;
}
