package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.BaseServiceInterface;
import com.example.welcometoesprit.repository.BaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
public class BaseServiceImp<T, id> implements BaseServiceInterface<T, id>{

    @Autowired
    BaseRepository<T, id> jpaRepo;

    @Override
    public List<T> retrieveAll() {
        return this.jpaRepo.findAll();
    }

    @Override
    public T add(T e) {
        return this.jpaRepo.save(e);
    }

    @Override
    public T update(T e) {
        return this.jpaRepo.save(e);
    }

    @Override
    public T retrieve(id id) {
        return this.jpaRepo.findById(id).orElse(null);
    }

    @Override
    public void remove(id id) {
        this.jpaRepo.deleteById(id);

    }









}
