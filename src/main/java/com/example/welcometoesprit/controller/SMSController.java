package com.example.welcometoesprit.controller;

import com.example.welcometoesprit.ServicesImpl.SmsServiceImp;
import com.example.welcometoesprit.entities.SmsSendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SMSController {


    @Autowired
    SmsServiceImp smsServiceImp;

    public SMSController(SmsServiceImp smsServiceImp) {
        this.smsServiceImp = smsServiceImp;
    }

    @PostMapping("/processSms")
    public String processSms(@RequestBody SmsSendRequest smsSendRequest){

        return smsServiceImp.sendSms(smsSendRequest.getDestinationSMSNumber(),smsSendRequest.getSmsMessage());
    }



}
