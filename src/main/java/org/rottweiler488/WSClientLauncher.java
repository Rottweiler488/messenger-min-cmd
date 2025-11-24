package org.rottweiler488;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
import jakarta.websocket.Session;
import org.rottweiler488.service.commandHandler.CommandHandler;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Consumer;

public class WSClientLauncher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        /*String address = "";
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
        }*/

        client.send(username);

        CommandHandler commandHandler = new CommandHandler();
        WSClient client = null;

        while (true) {
            String message = scanner.nextLine();

            if(!message.startsWith("/")) {
                try {
                    client.send(message);
                }
                catch (Exception e) {
                    System.out.println("No connection.");
                }
            }
            else {
                String argsMessage = message.substring(1);
                List<String> input = List.of(argsMessage.split("\\s+"));
                commandHandler.run(input);
            }
        }
    }
}
