package org.rottweiler488;

import org.rottweiler488.service.ClientConnection;
import org.rottweiler488.service.commandHandler.CommandHandler;

import java.util.List;
import java.util.Scanner;

public class WSClientLauncher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WSClient client = ClientConnection.client;//new WSClient();
        CommandHandler commandHandler = new CommandHandler();

        System.out.println("WELCOME TO THE MESSENGER MIN - CMD");

        //CLIENT LIFE CYCLE
        while (true) {
            String message = scanner.nextLine();

            if(!message.startsWith("/")) {
                client.send(message);
            }
            else {
                String argsMessage = message.substring(1);
                List<String> input = List.of(argsMessage.split("\\s+"));
                commandHandler.run(input);
            }
        }
    }
}
