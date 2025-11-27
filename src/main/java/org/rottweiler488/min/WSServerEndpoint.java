package org.rottweiler488.min;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.rottweiler488.min.model.MessageData;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        List<MessageData> loadedHistory = clientHistory.loadHistoryFromJsonFile();
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

        List<MessageData> messageData = clientHistory.loadHistoryFromJsonFile(); //Возможно так не делать, ведь данные могут обновиться.
        if (!Objects.isNull(messageData)) {
            StringBuilder historyStringBuilder = new StringBuilder();

            String dataTime, dataUsername, dataText, finalText = "";

            int toMessageDataIndex = messageData.size();// - 1;
            int fromMessageDataIndex = Math.max(toMessageDataIndex - historyMaxLength, 0);
            for (MessageData data : messageData.subList(fromMessageDataIndex, toMessageDataIndex)) {
                dataTime = data.getTime();
                dataUsername = data.getUsername();
                dataText = data.getText();

                finalText = "\n" + (dataUsername.isEmpty() ? dataText : String.format("(%s) %s: %s",dataTime, dataUsername, dataText));

                historyStringBuilder.append(finalText);
            }

            String fatHistory = historyStringBuilder.toString();

            try {
                session.getBasicRemote().sendText(fatHistory); //============================================================
            }
            catch (IOException e) {
                e.printStackTrace();
            }
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

        String text = String.format("(%s) %s: %s", formattedTime, username, message);//"(" + formattedTime + ") " + senderSession.getId() + ": " + message;
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