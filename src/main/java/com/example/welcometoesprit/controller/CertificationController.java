package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.CertificationServiceImp;
import com.example.welcometoesprit.ServicesImpl.DataMapper;
import com.example.welcometoesprit.entities.Event;
import com.example.welcometoesprit.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping(value = "/generate/document/{event}/{user}")
    public String generateDocument(@PathVariable("event") Event event, @PathVariable("user") User user) {

        String finalHtml = null;

        Context dataContext = dataMapper.setData(event,user);

        finalHtml = springTemplateEngine.process("CertifTemplate", dataContext);

        documentGenerator.htmlToPdf(finalHtml);

        return "Success";
    }
}
