package com.example.welcometoesprit.entities;

import com.example.welcometoesprit.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

  @Enumerated(EnumType.STRING)
  private Role role;

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
  private Set<FileInfo> files;

  @ManyToOne
  Classroom classroom;

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
