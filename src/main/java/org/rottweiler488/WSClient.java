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
        System.out.printf("Disconnected by reason: %s\n", reason.getReasonPhrase());
    }

    public void send(String message) {
        try {
            if (isConnected()) {
                session.getAsyncRemote().sendText(message);
            }
            else {
                throw new Exception();
            }
        }
        catch (Exception e) {
            System.out.println("No connection.");
        }
    }

    public void disconnect() {
        if (session.isOpen() && session != null) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.GOING_AWAY, "exit or closing application."));
                session = null; //Remove?
            }
            catch (Exception e) {
                System.out.println("Client is already disconnected.");
            }
        }
    }

    public boolean isConnected() {
        try {
            if (session != null || session.isOpen())
                return true;
        }
        catch (Exception e) {
            return false;
        }

        return false;
    }
}
