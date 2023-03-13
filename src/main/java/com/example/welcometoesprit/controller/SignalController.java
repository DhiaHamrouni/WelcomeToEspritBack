package com.example.welcometoesprit.controller;


import com.example.welcometoesprit.ServicesImpl.SignalServiceImpl;
import com.example.welcometoesprit.entities.SignalPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signal")
@CrossOrigin("*")
public class SignalController {
    
    @Autowired
    SignalServiceImpl signalService;
@PostMapping("/addSignal/{idUser}/{idpub}")

    public String AddSignal (@PathVariable Integer idUser ,@PathVariable Integer idpub){
        return signalService.AddSignalToPost(new SignalPost() , idUser , idpub);
    }
}
