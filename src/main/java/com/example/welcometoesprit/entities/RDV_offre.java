package com.example.welcometoesprit.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "RDV_offre")
public class RDV_offre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRdv")
    private Integer idRdv;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date schedule;
    private String room;
    private String assignment;
    private String notifications;

    @OneToOne(mappedBy = "rdv_offre")
    private CondidatOffre condidatOffre;
}
