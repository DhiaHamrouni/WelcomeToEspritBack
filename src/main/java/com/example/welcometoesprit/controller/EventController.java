package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.EventServiceImp;
import com.example.welcometoesprit.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@CrossOrigin("*")
public class EventController extends BaseController<Event,Integer> {

    @Autowired
    EventServiceImp eventServiceImp;

  /*  @GetMapping("/search")
    public ResponseEntity<List<Event>> search(@RequestParam("event") String event) {
        List<Event> results = eventServiceImp.searchByName(event);
        return ResponseEntity.ok(results);
    }*/
}
