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

    private LocalTime time = LocalTime.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    private final String formattedTime = time.format(formatter);

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

            String text = String.format("%s has connected.", message);
            sendSessions(text, session);
            return;
        }

        time = LocalTime.now();

        String username = (String) session.getUserProperties().get("username");
        if (Objects.isNull(username) || username.isEmpty()) {
            username = String.format("Guest-%s", session.getId());
        }

        String text = String.format("(%s) %s: %s", formattedTime, username, message);//"(" + formattedTime + ") " + senderSession.getId() + ": " + message;

        System.out.println(text);

        sendSessions(text, session);
    }

    @OnClose
    public void onClose(Session session) {
        String username = (String) session.getUserProperties().get("username");
        String text = String.format("%s disconnected.", username);

        sendSessions(text, session);
        sessions.remove(session);

        System.out.printf("%s: %s disconnected.\n", session.getId(), username);
    }

    private void sendSessions(String message, Session session) {
        for (Session s : sessions) {
            if (!s.equals(session) && s.isOpen()) {
                s.getAsyncRemote().sendText(message);
            }
        }
    }
}