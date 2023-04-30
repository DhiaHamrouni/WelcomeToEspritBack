package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServiceInterface.RatingServiceInterface;

import com.example.welcometoesprit.ServicesImpl.RatingServiceImp;
import com.example.welcometoesprit.entities.Rating;
import com.example.welcometoesprit.repository.RatingRepository;
import com.sun.mail.imap.protocol.Status;
import org.apache.poi.ss.formula.functions.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
@CrossOrigin("*")

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingServiceInterface ratingService;
    @PostMapping("/addRating/{idUser}/{idPub}/{rating}")
    public Rating addRating( @PathVariable Integer rating  , @PathVariable Integer idUser, @PathVariable Integer idPub) {


        Rating  Rate=new Rating();
      Rate.setScore(rating);
        return  ratingService.addRating(Rate , idUser, idPub );
    }

    @GetMapping("/getAllRating")
    public ResponseEntity<?> getRating() {
     return new ResponseEntity<>(ratingService.getAllRatings() , HttpStatus.OK);
    }

    @GetMapping("/getAllByIdPub/{id}")
    public ResponseEntity<?> getRatingsByPublicationId(@PathVariable Integer id) {
        List<Rating> ratings = ratingService.getRatingsByPublicationId(id);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/getAllByIdUSer/{id}")
    public ResponseEntity<?> getRatingsByUserId(@PathVariable Integer id) {
        List<Rating> ratings = ratingService.getRatingsByUserId(id);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRating/{id}")
    public void deleteRating(@PathVariable Integer id){
        ratingService.deleteRating(id);
    }

    @PutMapping("/update/{id}")
    public Rating updateRating(@RequestBody Rating rating,@PathVariable Integer id){
        return  ratingService.updateRating(rating,id);
    }


    @GetMapping("/getScore/{id}")
    public int getScore (@PathVariable Integer id){
        return ratingService.CalculeScorePublication(id);
    }

    @GetMapping("/getStatrate")
    public String getScore (){
        return ratingService.Stat();
    }
}
