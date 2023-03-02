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
@Table( name = "CondidatOffre")
public class CondidatOffre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOffre")
    private Integer idOffre; // Clé primaire
    private String Email;

    @OneToOne
    private FileEntity CV_motiv;
    @ManyToMany(mappedBy = "condidatOffres" , cascade = CascadeType.ALL)
    private Set<Offre> offres;
    @OneToOne
    private RDV_offre rdv_offre;
}
