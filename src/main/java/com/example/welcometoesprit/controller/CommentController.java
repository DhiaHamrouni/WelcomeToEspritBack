package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.CommentServiceImp;
import com.example.welcometoesprit.entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentController extends BaseController<Comment,Integer>{

    @Autowired
    CommentServiceImp commentServiceImp;
}
