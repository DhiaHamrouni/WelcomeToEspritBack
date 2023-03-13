package com.example.welcometoesprit.repository;

import com.example.welcometoesprit.entities.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends BaseRepository<Comment,Integer> {
    
}
