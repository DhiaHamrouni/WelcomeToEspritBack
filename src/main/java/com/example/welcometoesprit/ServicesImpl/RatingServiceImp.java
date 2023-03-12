package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.RatingServiceInterface;
import com.example.welcometoesprit.entities.Publication;
import com.example.welcometoesprit.entities.Rating;
import com.example.welcometoesprit.entities.ResourceNotFoundException;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.repository.PublicationRepository;
import com.example.welcometoesprit.repository.RatingRepository;
import com.example.welcometoesprit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class RatingServiceImp implements RatingServiceInterface {

    @Autowired
    RatingRepository ratingRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    PublicationRepository pubRepo;
    @Override
    public Rating addRating(Rating rating ,Integer IdPub , Integer IdUser ) {
        User u = userRepo.findById(IdUser).get();
        Publication p = pubRepo.findById(IdPub).get();
        rating.setUser(u);
        rating.setPublication(p);

        return ratingRepo.save(rating);
    }




    @Override
    public Rating updateRating(Rating rating ,Integer idRating) {
        Rating r = ratingRepo.findById(idRating).get();
        r.setScore(rating.getScore());
        return ratingRepo.saveAndFlush(r);
    }

    @Override
    public void deleteRating(Integer idRating) {
         ratingRepo.deleteById(idRating);
    }

    @Override
    public List<Rating> getAllRatings() {
        return  ratingRepo.findAll();
    }

    @Override
    public Rating getRatingById(Integer id) {
        return ratingRepo.findById(id).get();
    }

    @Override
    public List<Rating> getRatingsByPublicationId(Integer publicationId) {
        List<Rating> ll = new ArrayList<>();
        for (Rating r : ratingRepo.findAll()  )
        {
           if (r.getPublication().getIdPublication().equals(publicationId))
               ll.add(r);
        }
        return  ll;
    }

    @Override
    public List<Rating> getRatingsByUserId(Integer userId) {
        List<Rating> ll = new ArrayList<>();
        for (Rating r : ratingRepo.findAll()  )
        {
            if (r.getUser().getId().equals(userId))
                ll.add(r);
        }
        return  ll;
    }


    public int CalculeScorePublication(Integer idPub){
        int sc = 0;
        int x = 0;
        Publication pub = pubRepo.findById(idPub).get();
        for (Rating r : pub.getRatings()){
            sc = sc + r.getScore();
             x++;

        }
        return sc/x;
    }
}
