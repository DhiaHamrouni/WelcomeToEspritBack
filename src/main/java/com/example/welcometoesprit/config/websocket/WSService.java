package com.example.welcometoesprit.config.websocket;


public interface WSService {


    public void sendRequestNotificationToUser(final String userId, final Long requestId);

    public void sendRequestMessageToUser(final String userId, final Long requestId);
}
