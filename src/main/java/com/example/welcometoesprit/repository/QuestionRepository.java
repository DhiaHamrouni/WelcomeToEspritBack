package com.example.welcometoesprit.repository;

import com.example.welcometoesprit.entities.Category;
import com.example.welcometoesprit.entities.Interview;
import com.example.welcometoesprit.entities.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface QuestionRepository extends BaseRepository<Question,Integer> {
    List<Question> findByCategory(Category category);
}
