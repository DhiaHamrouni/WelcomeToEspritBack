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
@Table(name = "React")
@ToString
public class React implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReact;

   ReactEnum reactEnum;
    @JsonIgnore

    @ManyToOne
    private Publication publication;

    @JsonIgnore
    @ManyToOne
    private Comment comment;
    @JsonIgnore

    @ManyToOne
    private User user;
}