package com.example.welcometoesprit.config.websocket;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;



@Service
public class WSServiceImpl implements WSService {

    private final SimpMessagingTemplate messagingTemplate;

    public WSServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }



    @Override
    public void sendRequestNotificationToUser(String userId, Long requestId) {
        RequestNotification requestNotification = new RequestNotification(
                "vous avez une nouvelle notification", requestId);

        messagingTemplate.convertAndSend("/notification/" + userId, requestNotification);

    }

    @Override
    public void sendRequestMessageToUser(String userId, Long requestId) {
        RequestNotification requestNotification = new RequestNotification(
                "vous avez un nouveau message", requestId);

        messagingTemplate.convertAndSend("/topic/" + userId, requestNotification);

    }
}
