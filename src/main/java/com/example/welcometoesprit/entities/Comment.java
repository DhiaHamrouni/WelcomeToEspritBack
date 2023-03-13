package com.example.welcometoesprit.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table( name = "Comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComment")
    private Integer idComment; // Clé primaire
    private String content;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date datePublication;
    private Integer nbrLike;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<React> reacts;

    @JsonIgnore
    @ManyToOne
    User CommentPar;

    public boolean containsBadWords() {
        return false;
    }
}
