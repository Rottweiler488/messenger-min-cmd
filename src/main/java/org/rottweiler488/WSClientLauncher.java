package org.rottweiler488;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
import jakarta.websocket.Session;

import java.net.URI;
import java.util.Objects;
import java.util.Scanner;

public class WSClientLauncher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        String address = "";
        Session session = null;
        WSClient client = new WSClient();
        while (Objects.isNull(session)) {
            System.out.print("Connect to: ");
            address = scanner.nextLine();

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            try {
                session = container.connectToServer(client, new URI(String.format("ws://%s/ws/chat", address)));
            }
            catch (Exception e) {
                System.out.println("Failed to connect to the server at the specified address.");
            }
        }

        session = null; //Возможно удалить.
        client.send(username);

        String command;

        while (true) {
            String message = scanner.nextLine();
            if(!message.startsWith("/")){
                client.send(message);
            }
        }
    }
}
