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
    public void onMessage(String message, Session session) {
        System.out.println(message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.printf("Disconnected by reason: %s", reason.getReasonPhrase());
    }

    public void send(String message) {
        if (!Objects.isNull(session) && session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
    }
}
