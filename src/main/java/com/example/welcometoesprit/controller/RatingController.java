package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServiceInterface.RatingServiceInterface;

import com.example.welcometoesprit.entities.Rating;
import com.sun.mail.imap.protocol.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingServiceInterface ratingService;
    @PostMapping("addRating")
    public Rating addRating( @RequestBody Rating rating) {

        return  ratingService.addRating(rating);
    }

    @GetMapping("getAllRating")
    public ResponseEntity<?> getRating() {
     return new ResponseEntity<>(ratingService.getAllRatings() , HttpStatus.OK);
    }

    @GetMapping("getAllByIdPub/{id}")
    public ResponseEntity<?> getRatingsByPublicationId(@PathVariable Integer id) {
        List<Rating> ratings = ratingService.getRatingsByPublicationId(id);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("getAllByIdUSer/{id}")
    public ResponseEntity<?> getRatingsByUserId(@PathVariable Integer id) {
        List<Rating> ratings = ratingService.getRatingsByUserId(id);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}
