package com.example.welcometoesprit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WelcomeToEspritApplication {

    public static void main(String[] args) {
        SpringApplication.run(WelcomeToEspritApplication.class, args);
    }

}
