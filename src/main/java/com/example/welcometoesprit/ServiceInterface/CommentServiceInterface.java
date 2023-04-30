package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.Comment;

import java.util.List;

public interface CommentServiceInterface extends BaseServiceInterface<Comment, Integer>{
    List<Comment> getAllComments();
    List<Comment> getCommentsWithoutBadWords();
    public String addComment(Comment comment , Integer IdUser , Integer idP);
    public String updateComment(Comment comment , Integer IdComment, Integer IdUser);

}
