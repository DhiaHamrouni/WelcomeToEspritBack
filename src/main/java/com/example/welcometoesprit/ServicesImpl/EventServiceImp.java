package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.EventServiceInterface;
import com.example.welcometoesprit.entities.Event;
import com.example.welcometoesprit.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImp extends BaseServiceImp<Event,Integer> implements EventServiceInterface {

    @Autowired
    EventRepository eventRepository;


}
