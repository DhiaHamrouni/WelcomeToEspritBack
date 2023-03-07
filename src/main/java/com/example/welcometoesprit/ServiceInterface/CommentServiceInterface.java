package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.Comment;

import java.util.List;

public interface CommentServiceInterface extends BaseServiceInterface<Comment, Integer>{
    List<Comment> getAllComments();
    List<Comment> getCommentsWithoutBadWords();
    String addComment(Comment comment);
}
