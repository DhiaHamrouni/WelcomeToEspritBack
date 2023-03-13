package com.example.welcometoesprit.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class SignalPost implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idSignal;
        @JsonFormat(pattern = "MM/dd/yyyy")
        private Date DateSignal;
        @JsonIgnore
        @ManyToOne
        private Publication publication;
        @JsonIgnore

        @ManyToOne
        private User signalPar;
    }

