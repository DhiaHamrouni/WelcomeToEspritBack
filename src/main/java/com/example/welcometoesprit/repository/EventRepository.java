package com.example.welcometoesprit.repository;

import com.example.welcometoesprit.entities.Event;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends BaseRepository<Event,Integer> {
}
