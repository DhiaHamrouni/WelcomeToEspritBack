package com.example.welcometoesprit.token;

import com.example.welcometoesprit.entities.CondidatOffre;
import com.example.welcometoesprit.entities.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Token {

  @Id
  @GeneratedValue
  public Integer id;

  @Column(unique = true)
  public String token;

  @Enumerated(EnumType.STRING)
  public TokenType tokenType = TokenType.BEARER;

  public boolean revoked;

  public boolean expired;

  @Override
  public String toString() {
    return "Token{" +
            "id=" + id +
            ", token='" + token + '\'' +
            ", tokenType=" + tokenType +
            ", revoked=" + revoked +
            ", expired=" + expired +
            ", user=" + user +
            '}';
  }

  @ManyToOne
  @JoinColumn(name = "user_id")
  public User user;
  @ManyToOne
  @JoinColumn(name = "idOffre")
  public CondidatOffre condidatOffre;
}
