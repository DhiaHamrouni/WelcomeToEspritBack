package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.CommentServiceInterface;
import com.example.welcometoesprit.entities.Comment;
import com.example.welcometoesprit.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImp extends BaseServiceImp<Comment,Integer> implements CommentServiceInterface {

    @Autowired
    CommentRepository commentRepository;
}
