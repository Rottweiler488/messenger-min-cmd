package org.rottweiler488.min.service;

import jakarta.websocket.ContainerProvider;

import jakarta.websocket.WebSocketContainer;
import org.rottweiler488.min.WSClient;

import java.net.URI;

public class ClientConnection { //Renaming?
    public static final String SERVER_ADDRESS = "localhost:4052";

    public static WSClient client = new WSClient();
    public static WebSocketContainer container = ContainerProvider.getWebSocketContainer();

    public static void connect(String address) {//(String chatName) {
        if (client.isConnected()) {
            System.out.println("Client is already connected.");
            return;
        }

        try {
            container.connectToServer(client, new URI(String.format("ws://%s/ws/chat", address)));//String.format("ws://%s/ws/chat//s/%s", SERVER_ADDRESS, chatName)));
        }
        catch (Exception e) {
            System.out.println("Failed to connect to the server at the specified address.");
        }
    }

    public static void disconnect() {
        if (!client.isConnected()) {
            System.out.println("Client is not connected.");
            return;
        }

        client.disconnect();
    }
}
