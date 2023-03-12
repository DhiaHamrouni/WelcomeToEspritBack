package com.example.welcometoesprit.entities;

import com.example.welcometoesprit.token.Token;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Integer id;
  private String firstname;
  private String lastname;
  private String username;
  private String email;
  @Enumerated(EnumType.STRING)
  private Sexe sexe;
  @Enumerated(EnumType.STRING)
  private Nationality nationality;
  @Enumerated(EnumType.STRING)
  private TypeCours typeCours;
  private String password;
  private String identifiant;

  private LocalDateTime registrationDate;
  private String cin;
  private Boolean locked=false;
  private Boolean enabled=false;
  @Enumerated(EnumType.STRING)
  private Role role;

  @Enumerated(EnumType.STRING)
  private NiveauActuel niveauActuel;
  @Enumerated(EnumType.STRING)
  private NiveauSuivant niveauSuivant;

  public User(String firstname, String lastname, String email, String password, Role role) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public User(String firstname, String lastname, String email,String cin, String password, Nationality nationality, TypeCours typeCours, Sexe sexe, NiveauActuel niveauActuel,  Role role,LocalDateTime registrationDate) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.sexe = sexe;
    this.nationality = nationality;
    this.typeCours = typeCours;
    this.password = password;
    this.cin = cin;
    this.role = role;
    this.niveauActuel = niveauActuel;
    this.registrationDate=registrationDate;
  }

  @Override
  public String toString() {
    return "Student : \n" +
            ", firstname='" + firstname + '\n' +
            ", lastname='" + lastname + '\n'
            ;
  }



  @OneToMany(mappedBy = "user")
  private List<Token> tokens;


  @ManyToOne
  Event event;
  @ManyToOne
  Event event2;
  @OneToMany(cascade= CascadeType.ALL, mappedBy = "complaintPar")
  List<Complaint> listOfComplaints;

  @OneToMany(cascade= CascadeType.ALL, mappedBy = "publierPar")
  List<Publication> listOfPublication;

  @ManyToMany(mappedBy = "likerPar",cascade = CascadeType.ALL)
  private Set<Publication> listPublicationLikee;

  @ManyToOne
  Realisation realisation;

  @OneToOne
  Interview interviewStudent;

  @ManyToMany(cascade = CascadeType.ALL)
  private Set<Interview> InterviewEvaluators;

  @OneToMany(cascade = CascadeType.ALL)
  private Set<FileEntity> files;

  @ManyToMany(cascade= CascadeType.ALL)
  private Set<Classroom> classroomSet;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
