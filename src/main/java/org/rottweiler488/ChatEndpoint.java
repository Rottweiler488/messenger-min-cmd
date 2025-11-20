package org.rottweiler488;

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
    public void onMessage(String message, Session session) {
        if ("Unknown".equals(session.getUserProperties().get("username"))) {
            session.getUserProperties().put("username", message);
            return;
        }

        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = time.format(formatter);

        String username = (String) session.getUserProperties().get("username");
        if (Objects.isNull(username) || username.isEmpty()) {
            username = String.format("Guest-%s", session.getId());
        }

        String text = String.format("(%s) %s: %s", formattedTime, username, message);//"(" + formattedTime + ") " + senderSession.getId() + ": " + message;

        System.out.println(text);

        for (Session s : sessions) {
            if (!s.equals(session) && s.isOpen()) {
                s.getAsyncRemote().sendText(text);
            }
        }
    }

    //TODO: Добавить уведомление всем подключённым.
    @OnClose
    public void onClose(Session session) {
        String username = (String) session.getUserProperties().get("username");
        sessions.remove(session);
        System.out.printf("%s disconnected", username);
    }
}
