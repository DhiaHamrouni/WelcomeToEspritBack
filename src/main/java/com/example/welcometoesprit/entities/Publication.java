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

    private double rating;
    @Override
    public String toString() {
        return "Publication{" +
                "idPublication=" + idPublication +
                ", sujet='" + sujet + '\'' +
                ", contenu='" + contenu + '\'' +
                ", rating='" + rating + '\'' +
                ", publierPar=" + publierPar +
                ", likerPar=" + likerPar +
                ", listOfComments=" + listOfComments +
                '}';
    }
    @JsonIgnore
    @ManyToOne
    User publierPar;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "publication")
    List<SignalPost> signalPosts;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> likerPar;

    @OneToMany(cascade = CascadeType.ALL)
    List<Comment> listOfComments;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    private List<React> reacts;
}
