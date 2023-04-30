package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.CommentServiceInterface;
import com.example.welcometoesprit.entities.Comment;
import com.example.welcometoesprit.entities.Publication;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.repository.CommentRepository;
import com.example.welcometoesprit.repository.PublicationRepository;
import com.example.welcometoesprit.repository.UserRepository;
import com.pidev.phset.services.BadWordImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImp extends BaseServiceImp<Comment,Integer> implements CommentServiceInterface {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PublicationRepository publicationRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Comment> getAllComments() {
        return null;
    }

    @Override
    public List<Comment> getCommentsWithoutBadWords() {
        return null;
    }

    @Override
    public String addComment(Comment comment , Integer IdUser , Integer idPost) {
        Publication p = publicationRepository.findById(idPost).orElse(null);
        User usr = userRepository.findById(IdUser).orElseThrow(null);
        comment.setCommentPar(usr);
        List<Comment> ll = new ArrayList<>();
        ll = p.getListOfComments();
        ll.add(comment);
        p.setListOfComments(ll);
        com.pidev.phset.services.BadWordImpl badWord= new BadWordImpl();
        if(  badWord.filterText(comment.getContent()).equals("This post contain bad word"))
        return "This post contain bad word";
        else {
            commentRepository.save(comment);
            publicationRepository.save(p);
            return "add successfuly";
        }
    }
    @Override
    public String updateComment(Comment comment , Integer IdComment, Integer IdUser) {
        User usr = userRepository.findById(IdUser).orElseThrow(null);
        Comment com =commentRepository.findById(IdComment).get();
        comment.setCommentPar(usr);
        com.pidev.phset.services.BadWordImpl badWord= new BadWordImpl();
        if(  badWord.filterText(comment.getContent()).equals("This post contain bad word"))
            return "This post contain bad word";
        else {
            commentRepository.save(com);
            return "add successfuly";
        }
    }

    @Service
    public class CommentService {
        @Autowired
        private CommentRepository commentRepository;

        public List<Comment> getCommentsWithoutBadWords() {
            List<Comment> allComments = commentRepository.findAll();
            List<Comment> commentsWithoutBadWords = new ArrayList<>();
            for (Comment comment : allComments) {
                if (!comment.containsBadWords()) {
                    commentsWithoutBadWords.add(comment);
                }
            }
            return commentsWithoutBadWords;
        }
    }
}
