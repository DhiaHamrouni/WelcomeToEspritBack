package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.CertificationServiceImp;
import com.example.welcometoesprit.ServicesImpl.DataMapper;
import com.example.welcometoesprit.entities.Event;
import com.example.welcometoesprit.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@RestController
public class CertificationController {

    @Autowired
    private CertificationServiceImp documentGenerator;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DataMapper dataMapper;

    @GetMapping(value = "/generate/document/{id_event}/{id_user}")
    public String generateDocument(@PathVariable("id_event") Integer id_event, @PathVariable("id_user") Integer id_user) {

        String finalHtml = null;

        Context dataContext = dataMapper.setData(id_event,id_user);

        finalHtml = springTemplateEngine.process("CertifTemplate", dataContext);

        documentGenerator.htmlToPdf(finalHtml);

        return "Success";
    }
}
