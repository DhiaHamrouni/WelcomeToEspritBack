package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.CommentServiceImp;
import com.example.welcometoesprit.entities.Comment;
import com.example.welcometoesprit.entities.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentController  extends BaseController<Comment,Integer> {

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

        @PostMapping("/addComment/{idUser}/{idPost}")
        public ResponseEntity<?> addComment(@RequestBody Comment comment ,@PathVariable Integer idUser,@PathVariable Integer idPost) {
           return new ResponseEntity<>(commentService.addComment(comment , idUser , idPost), HttpStatusCode.valueOf(200));
        }

    @PostMapping("/updateComment/{idComment}/{idUser}")
    public ResponseEntity<?> updateComment(@RequestBody Comment comment ,@PathVariable Integer idComment, @PathVariable Integer idUser) {
        return new ResponseEntity<>(commentService.updateComment(comment, idComment,idUser), HttpStatusCode.valueOf(200));
    }


    }


