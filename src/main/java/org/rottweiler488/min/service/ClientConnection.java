package org.rottweiler488.min.service;

import jakarta.websocket.ContainerProvider;

import jakarta.websocket.WebSocketContainer;
import org.rottweiler488.min.WSClient;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class ClientConnection implements NetworkClient { //Renaming?
    public static final String SERVER_ADDRESS = "localhost:4052";

    public WSClient client = new WSClient();
    public WebSocketContainer container = ContainerProvider.getWebSocketContainer();

    @Override
    public CompletableFuture<Boolean> connect(String host, int port) {
        if (client.isConnected()) {
            return CompletableFuture.completedFuture(false);
        }

        try {
            String address = host + ":" + port;
            //String.format("ws://%s/ws/chat//s/%s", SERVER_ADDRESS, chatName)));
            container.connectToServer(client, new URI(String.format("ws://%s/ws/chat", address)));
            return CompletableFuture.completedFuture(true);
        }
        catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public CompletableFuture<Void> sendMessage(String text) {
        if (!client.isConnected()) {
            return CompletableFuture.failedFuture(new Exception());
        }

        client.send(text);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> disconnect() {
        if (!client.isConnected()) {
            return CompletableFuture.failedFuture(new Exception());
        }

        client.disconnect();
        return CompletableFuture.completedFuture(null);
    }
}
