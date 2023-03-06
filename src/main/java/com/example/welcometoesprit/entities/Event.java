package com.example.welcometoesprit.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "Event")
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvent")
    private Integer idEvent; // Clé primaire
    private String location;
    private String eventName;
    private String tools;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date endDate;
    private float budget;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "event")
    List<User> listOfParticipant;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "event2")
    List<User> speakerList;
}
