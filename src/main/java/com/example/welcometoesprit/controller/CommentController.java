package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.CommentServiceImp;
import com.example.welcometoesprit.entities.Bloc;
import com.example.welcometoesprit.entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentController {

    @Autowired
    CommentServiceImp commentService;





        @GetMapping
        public List<Comment> getAllComments() {
            return commentService.getAllComments();
        }

        @GetMapping("/without-bad-words")
        public List<Comment> getCommentsWithoutBadWords() {
            return commentService.getCommentsWithoutBadWords();
        }

        @PostMapping("/addComment")
        public ResponseEntity<?> addComment(@RequestBody Comment comment) {
           return new ResponseEntity<>(commentService.addComment(comment), HttpStatusCode.valueOf(200));
        }
    }


