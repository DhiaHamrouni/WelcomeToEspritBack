package com.example.welcometoesprit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Rating")
@ToString
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRating;

    private Integer score;
    @JsonIgnore

    @ManyToOne
    private Publication publication;
    @JsonIgnore

    @ManyToOne
    private User user;
}