package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.entities.Event;
import com.example.welcometoesprit.entities.User;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataMapper {
    public Context setData(Event event , User user) {

        Context context = new Context();

        Map<String, Object> data = new HashMap<>();

        data.put("event", event);
        data.put("user", user);

        context.setVariables(data);

        return context;
    }
}
