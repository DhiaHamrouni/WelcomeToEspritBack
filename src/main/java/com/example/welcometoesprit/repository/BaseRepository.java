package com.example.welcometoesprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
@NoRepositoryBean
public interface BaseRepository <T,id>extends JpaRepository<T, id> {
}
