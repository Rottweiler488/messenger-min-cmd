package org.rottweiler488;

import jakarta.websocket.*;

import java.util.Objects;

@ClientEndpoint
public class WSClient {
    private Session session;

    @OnOpen
    public  void onOpen(Session session) {
        this.session = session;

        System.out.println("Connected to server.");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }

    public void send(String message) {
        if (!Objects.isNull(session) && session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
    }
}
