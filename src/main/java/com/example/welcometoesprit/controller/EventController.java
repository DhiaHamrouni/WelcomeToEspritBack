package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.EventServiceImp;
import com.example.welcometoesprit.entities.Comment;
import com.example.welcometoesprit.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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

    @GetMapping("get-All")
    public List<Event> getAll(){
        return eventServiceImp.getAllEvent();
    }

    @PostMapping("/addEvent")
    public ResponseEntity<?> addEvent(@RequestBody Event event) {
        return new ResponseEntity<>(eventServiceImp.addEvent(event), HttpStatusCode.valueOf(200));
    }

    /*@PutMapping("/updateEvent/{id}")
    public ResponseEntity<?> updateEvent(@RequestBody Event event,@PathVariable("id") Integer idEvent ) {
        Event e =new Event();
        if (e==null)
        {
            return  ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(eventServiceImp.updateEvent(event), HttpStatusCode.valueOf(200));
    }*/
}
