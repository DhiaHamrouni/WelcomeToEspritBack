package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.Rating;
import com.example.welcometoesprit.repository.BaseRepository;

import java.util.List;

public interface RatingServiceInterface  {

        public Rating addRating(Rating rating ,Integer IdPub , Integer IdUser ) ;
        public Rating updateRating(Rating rating ,Integer idRating);
        void deleteRating(Integer id);
        List<Rating> getAllRatings();
        Rating getRatingById(Integer id);
        List<Rating> getRatingsByPublicationId(Integer publicationId);
        List<Rating> getRatingsByUserId(Integer userId);

        public int CalculeScorePublication(Integer idPub);





        }
