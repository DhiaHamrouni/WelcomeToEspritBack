package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.entities.Event;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.repository.EventRepository;
import com.example.welcometoesprit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DataMapper {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;
    public Context setData(Integer id_event , Integer id_user) {
        Optional<User> user = userRepository.findById(id_user);
        Optional<Event> event =eventRepository.findById(id_event);

        Context context = new Context();

        Map<String, Object> data = new HashMap<>();

        data.put("event", event);
        data.put("user", user);

        context.setVariables(data);

        return context;
    }
}
