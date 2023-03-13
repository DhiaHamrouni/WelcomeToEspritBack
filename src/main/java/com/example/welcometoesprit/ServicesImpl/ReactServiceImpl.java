package com.example.welcometoesprit.ServicesImpl;


import com.example.welcometoesprit.entities.*;
import com.example.welcometoesprit.repository.CommentRepository;
import com.example.welcometoesprit.repository.PublicationRepository;
import com.example.welcometoesprit.repository.Reactrepository;
import com.example.welcometoesprit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactServiceImpl {
@Autowired
    Reactrepository reactrepository;

@Autowired
    PublicationRepository publicationRepository;

@Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;

    public React addReact(React react, Integer idUsr , Integer idPost ) {
        User u = userRepository.findById(idUsr).orElse(null);
        Publication pub = publicationRepository.findById(idPost).orElse(null);
        react.setUser(u);
        react.setPublication(pub);
        reactrepository.save(react);
        return react;
    }

    public React addReactToComment(React react, Integer idUsr , Integer idComment ) {
        User u = userRepository.findById(idUsr).orElse(null);
        Comment c = commentRepository.findById(idComment).orElse(null);
        react.setUser(u);
        react.setComment(c);
        reactrepository.save(react);
        return react;
    }
    public List<React> getCommentReacts (Integer idcomm)
    {
        Comment c = commentRepository.findById(idcomm).orElse(null);
        return c.getReacts();
    }
    public List<React> getPostReacts (Integer idPost)
    {
        Publication pub = publicationRepository.findById(idPost).orElse(null);
        return pub.getReacts();
    }

    public String getPostNbrReacts (Integer idPost , ReactEnum reactEnum)
    {
        int i =0;
        Publication pub = publicationRepository.findById(idPost).orElse(null);
        for ( React r: pub.getReacts()
             ) {

            if (r.getReactEnum().equals(reactEnum) )
            { i++;}
        }
        return "this post resive " + i + " " + reactEnum ;
    }

    public void DeleteReact (Integer idReact)
    {
        reactrepository.deleteById(idReact);
    }

    public React updateReact(React react , Integer Idreact)
    {
        React r = reactrepository.findById(Idreact).get();
        r.setReactEnum(react.getReactEnum());
        return reactrepository.save(r);
    }
}
