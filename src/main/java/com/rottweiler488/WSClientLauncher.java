package com.rottweiler488;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;

import java.net.URI;
import java.util.Scanner;

public class WSClientLauncher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();

        System.out.println("Connect to: ");
        String adress = "";
        while (adress.isEmpty())
            adress = scanner.nextLine();

        WSClient client = new WSClient();
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            container.connectToServer(client, new URI(String.format("ws://%s/ws/chat", adress)));
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }

        client.send(username);

        while (true) {
            String message = scanner.nextLine();
            client.send(message);
        }
    }
}
