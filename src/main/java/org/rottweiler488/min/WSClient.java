package org.rottweiler488.min;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.Session;
import jakarta.websocket.OnOpen;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnClose;
import jakarta.websocket.CloseReason;

@ClientEndpoint
public class WSClient {
    private Session session;

    @OnOpen
    public  void onOpen(Session session) { this.session = session; }

    @OnMessage
    public void onMessage(String message, Session session) { System.out.println(message); }

    @OnClose
    public void onClose(Session session, CloseReason reason) {}

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
                //System.out.println("Client is already disconnected.");
            }
        }
    }

    public boolean isConnected() {
        try { return (session != null || session.isOpen()); }
        catch (Exception e) { return false; }
    }
}
