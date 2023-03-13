package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.EventServiceInterface;
import com.example.welcometoesprit.entities.Event;
import com.example.welcometoesprit.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImp extends BaseServiceImp<Event,Integer> implements EventServiceInterface {

    @Autowired
    EventRepository eventRepository;
    @Override
    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getById(Integer id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void deletEventById(Integer id) {
         eventRepository.deleteById(id);
    }
}
