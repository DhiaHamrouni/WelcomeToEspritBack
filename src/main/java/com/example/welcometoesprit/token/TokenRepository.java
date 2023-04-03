package com.example.welcometoesprit.token;

import com.example.welcometoesprit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(Integer id);

  Optional<Token> findByToken(String token);
  @Query(value = """
      select t.user.email from Token t inner join User u\s
      on t.user.id = u.id\s
      where t.token= :token and (t.expired = false or t.revoked = false)\s
      """)
  String getEmailByToken(String token);
}
