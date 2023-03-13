package com.example.welcometoesprit.entities;

import com.example.welcometoesprit.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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
  private String password;
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

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", role=" + role +
            '}';
  }



  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @OneToMany(mappedBy = "user")
  private List<Rating> ratings;
  @ManyToOne
  Event event;
  @ManyToOne
  Event event2;
  @OneToMany(cascade= CascadeType.ALL, mappedBy = "complaintPar")
  List<Complaint> listOfComplaints;

  @OneToMany(cascade= CascadeType.ALL, mappedBy = "publierPar")
  List<Publication> listOfPublication;

  @OneToMany(cascade= CascadeType.ALL, mappedBy = "signalPar")
  List<SignalPost> signalPosts;
  @ManyToMany(mappedBy = "likerPar",cascade = CascadeType.ALL)
  private Set<Publication> listPublicationLikee;

  @OneToMany(mappedBy = "CommentPar",cascade = CascadeType.ALL)
  private Set<Comment> comments;

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
