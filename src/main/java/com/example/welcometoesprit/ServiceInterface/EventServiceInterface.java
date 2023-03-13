package com.example.welcometoesprit.ServiceInterface;

import com.example.welcometoesprit.entities.Event;

import java.util.List;
import java.util.Optional;

public interface EventServiceInterface extends BaseServiceInterface<Event,Integer>{
    public List<Event> getAllEvent();
    public Optional<Event> getById(Integer id);
    public Event addEvent(Event event);
    public Event updateEvent(Event event);
    public void deletEventById(Integer id);

}
