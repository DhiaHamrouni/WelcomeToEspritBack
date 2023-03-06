package com.example.welcometoesprit.ServiceInterface;

public interface SmsServiceInterface {

    public String sendSms(String smsNumber,String smsMessage);

    public void sendSmsReminders();
    }
