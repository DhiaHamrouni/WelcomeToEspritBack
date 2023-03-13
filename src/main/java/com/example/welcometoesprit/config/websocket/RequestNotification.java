package com.example.welcometoesprit.config.websocket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestNotification {
    private String message = "";

    private Long requestId;
}
