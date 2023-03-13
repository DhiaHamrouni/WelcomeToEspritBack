package com.example.welcometoesprit.repository;

import com.example.welcometoesprit.entities.Nationality;
import com.example.welcometoesprit.entities.Role;
import com.example.welcometoesprit.entities.Sexe;
import com.example.welcometoesprit.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {

  User findByUsername(String Username);


  Optional<User> findByEmail(String email);
  @Transactional
  @Modifying
  @Query("UPDATE User a " +
          "SET a.enabled = TRUE WHERE a.email = ?1")
  int enableAppUser(String email);

  List<User> findByRole(Role role);

  int countUserByRoleAndSexe(Role role, Sexe sexe);
  int countUserByRoleAndNationality(Role role, Nationality nationality);
  @Query("SELECT COUNT(u) FROM User u WHERE u.role =?1 AND YEAR(u.registrationDate) = ?2")
  int UserByRoleAndRegistDate(Role role, int year);
}
