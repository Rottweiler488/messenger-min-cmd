package org.rottweiler488.min;

import jakarta.websocket.Session;
import jakarta.websocket.OnOpen;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.server.ServerEndpoint;
import org.rottweiler488.min.model.MessageData;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/chat")///{chatName}")
public class WSServerEndpoint {
    //private static final Map<String, ConcurrentHashMap<String, Session>> chats = new ConcurrentHashMap<>();

    public static final Set<Session> sessions = new CopyOnWriteArraySet<>();
    public static final List<MessageData> messagesData = new CopyOnWriteArrayList<>();

    private static final int historyMaxLength = 50;
    private static final JsonClientHistory clientHistory = new JsonClientHistory(historyMaxLength);

    private LocalTime time = LocalTime.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    private final String formattedTime = time.format(formatter);

    static {
        List<MessageData> loadedHistory = clientHistory.loadListOfHistoryFromJsonFile();
        if (!loadedHistory.isEmpty()) {
            messagesData.addAll(loadedHistory);
            System.out.printf("Loaded %d messages\n", loadedHistory.size());
        }
    }

    @OnOpen
    public void onOpen(Session session) {//, @PathParam("chatName") String chatName) {
        session.getUserProperties().put("username", "Unknown");
        sessions.add(session);
        System.out.println("Client connection: " + session.getId());

        try {
            String history = clientHistory.loadStringOfHistoryFromJsonFile();
            session.getBasicRemote().sendText(history);
        }
        catch (IOException e) {
            System.out.println("The story could not be loaded.");
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if ("Unknown".equals(session.getUserProperties().get("username"))) {
            session.getUserProperties().put("username", message);

            String text = String.format("%s has connected.", message);
            messagesData.add(new MessageData("", "", text));
            clientHistory.saveHistoryToJsonFile(messagesData);//, direction);
            sendSessions(text, session);
            return;
        }

        time = LocalTime.now();
        String username = (String) session.getUserProperties().get("username");
        if (Objects.isNull(username) || username.isEmpty()) {
            username = String.format("Guest-%s", session.getId());
        }

        //"(" + formattedTime + ") " + senderSession.getId() + ": " + message;
        String text = String.format("(%s) %s: %s", formattedTime, username, message);
        messagesData.add(new MessageData(username, formattedTime, message));
        clientHistory.saveHistoryToJsonFile(messagesData);//, direction);
        sendSessions(text, session);

        System.out.println(text);
    }

    @OnClose
    public void onClose(Session session) {
        String username = (String) session.getUserProperties().get("username");
        String text = String.format("%s disconnected.", username);

        messagesData.add(new MessageData("", "", text));
        clientHistory.saveHistoryToJsonFile(messagesData);//, direction);
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