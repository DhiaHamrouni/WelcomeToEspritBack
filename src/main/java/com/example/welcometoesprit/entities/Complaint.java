package com.example.welcometoesprit.entities;

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
@Table( name = "Complaint")
public class Complaint   implements Serializable{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idComplaint;
    private String subject;
    private String content;
    @Temporal(TemporalType.DATE)
    private Date date ;
    @Enumerated(EnumType.STRING)
    private StatusComplaint status;

    @ManyToOne(cascade = CascadeType.ALL)
    User complaintPar;

}




