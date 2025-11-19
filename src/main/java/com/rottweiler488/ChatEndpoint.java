package com.rottweiler488;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/chat")
public class ChatEndpoint {
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        session.getUserProperties().put("username", "Unknown");
        sessions.add(session);
        System.out.println("Client connection: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session senderSession) {
        if ("Unknown".equals(senderSession.getUserProperties().get("username"))) {
            senderSession.getUserProperties().put("username", message);
            return;
        }

        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = time.format(formatter);

        String username = (String) senderSession.getUserProperties().get("username");
        if (Objects.isNull(username) || username.isEmpty()) {
            username = String.format("Guest-%s", senderSession.getId());
        }

        String text = String.format("(%s) %s: %s", formattedTime, username, message);//"(" + formattedTime + ") " + senderSession.getId() + ": " + message;

        System.out.println(text);

        for (Session session : sessions) {
            if (!session.equals(senderSession) && session.isOpen()) {
                session.getAsyncRemote().sendText(text);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        String username = (String) session.getUserProperties().get("username");
        sessions.remove(session);
        System.out.printf("%s disconnected", username);
    }
}
