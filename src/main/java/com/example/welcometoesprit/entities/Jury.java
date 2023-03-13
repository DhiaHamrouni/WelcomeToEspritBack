package com.example.welcometoesprit.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "Jury")

public class Jury implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jury")
    private Integer id_jury;
    private String First_Name;
    private String Last_Name;
    private String position;
    private String notification;


    @ManyToMany(cascade = CascadeType.ALL)
    private List<RDV_offre> rdvOffreList;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "jury")
    private List<Criteria> criteriaList;


}
