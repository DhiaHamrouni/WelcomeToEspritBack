package com.example.welcometoesprit.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "Criteria")
public class Criteria implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCriteria")
    private Integer idCriteria; // Clé primaire
    private Questions Question;
    private Integer Value;

    @ManyToOne(cascade = CascadeType.ALL)
    Jury jury;
}
