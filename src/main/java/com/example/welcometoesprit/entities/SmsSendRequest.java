package com.example.welcometoesprit.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SmsSendRequest implements Serializable {
    private String destinationSMSNumber;
    private String smsMessage;
}
