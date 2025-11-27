package org.rottweiler488.min.service.chat;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
import org.rottweiler488.min.WSClient;

import java.net.URI;

public class ChatJoin { //Может быть использовать, а команду сделать лишь обёрткой.
    public WSClient join(String address) {
        WSClient client = null;
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        try {
            container.connectToServer(client, new URI(String.format("ws://%s/ws/chat", address)));
        }
        catch (Exception e) {
            System.out.println("Failed to connect to the server at the specified address.");
            return null;
        }

        return client;
    }
}
