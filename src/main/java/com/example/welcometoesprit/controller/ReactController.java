package com.example.welcometoesprit.controller;


import com.example.welcometoesprit.ServicesImpl.ReactServiceImpl;
import com.example.welcometoesprit.entities.React;
import com.example.welcometoesprit.entities.ReactEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/react")

@RestController
public class ReactController {

    @Autowired
    ReactServiceImpl reactService;


    @PostMapping("/addReact/{IdUser}/{IdPost}")
    public React AddReact (@RequestBody React react , @PathVariable Integer IdUser , @PathVariable Integer IdPost)
    {
     return    reactService.addReact(react,IdUser,IdPost);

    }

    @PostMapping("/addReactComment/{IdUser}/{IdCom}")
    public React AddReactComm (@RequestBody React react , @PathVariable Integer IdUser , @PathVariable Integer IdCom)
    {
        return    reactService.addReactToComment(react,IdUser,IdCom);
    }

    @GetMapping("/getCommentReact/{IdCom}")
    public List<React> getComReact(@PathVariable Integer IdCom)
    {return reactService.getCommentReacts(IdCom);}


    @GetMapping("/getPostReact/{Idpost}")
    public List<React> getPostReact(@PathVariable Integer Idpost)
    {return reactService.getPostReacts(Idpost);}


    @GetMapping("/getPostNbrReact/{Idpost}/{enume}")
    public String getPostReact(@PathVariable Integer Idpost , @PathVariable ReactEnum enume)
    {return reactService.getPostNbrReacts(Idpost , enume);}

    @PutMapping("/UpdateReact/{Idreact}")
    public React updatereact(@RequestBody React r , @PathVariable Integer Idreact)
    {return reactService.updateReact(r , Idreact);}

    @DeleteMapping("/deleteReact/{Idreact}")
    public void deleteReact( @PathVariable Integer Idreact)
    {
        reactService.DeleteReact(Idreact);
    }
}
