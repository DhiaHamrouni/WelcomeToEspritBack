package com.example.welcometoesprit.entities;

import com.example.welcometoesprit.token.Token;
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
@Table( name = "CondidatOffre")
public class CondidatOffre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOffre")
    private Integer idOffre; // Clé primaire
    private String FirstName;
    private String LastName;
    private String email;
    private String Notification;
    private boolean enableCondidatOffre = false;
    @OneToOne
    private FileEntity CV_motiv;
    //@ManyToMany(mappedBy = "condidatOffres" , cascade = CascadeType.ALL)
    //private List<Offre> offres;
    @OneToOne
    private RDV_offre rdv_offre;
    @OneToMany(mappedBy = "condidatOffre")
    private List<Token> tokens;
    @OneToOne(mappedBy = "condidatOffreResult")
    Result result;

}
