package com.example.welcometoesprit.entities;

import com.example.welcometoesprit.token.Token;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
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
public class User implements UserDetails, Serializable {

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

  @JsonIgnore
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

  private String numTel;

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


  @JsonIgnore
  @Enumerated(EnumType.STRING)
  private Status status=Status.Active;
  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @JsonIgnore
  private Integer warnings=0;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Rating> ratings;
  @ManyToOne
  @JsonIgnore
  Event event;
  @JsonIgnore
  @ManyToOne
  Event event2;
  @JsonIgnore
  @OneToMany(cascade= CascadeType.ALL, mappedBy = "complaintPar")
  List<Complaint> listOfComplaints;
  @JsonIgnore

  @OneToMany(cascade= CascadeType.ALL, mappedBy = "publierPar")
  List<Publication> listOfPublication;
  @JsonIgnore
  @OneToMany(cascade= CascadeType.ALL, mappedBy = "signalPar")
  List<SignalPost> signalPosts;
  @JsonIgnore
  @OneToMany(cascade= CascadeType.ALL, mappedBy = "user")
  List<React> reacts;
  @JsonIgnore
  @ManyToMany(mappedBy = "likerPar",cascade = CascadeType.ALL)
  private Set<Publication> listPublicationLikee;
  @JsonIgnore
  @OneToMany(mappedBy = "CommentPar",cascade = CascadeType.ALL)
  private Set<Comment> comments;
  @JsonIgnore
  @ManyToOne
  Realisation realisation;

  @OneToOne
  @JsonIgnore
  Interview interviewStudent;


  @OneToMany(cascade = CascadeType.ALL,mappedBy = "evaluator")
  @JsonIgnore
  private Set<Interview> InterviewEvaluators;
  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL)
  private Set<FileEntity> files;
  @JsonIgnore
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
