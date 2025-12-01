package org.rottweiler488.min;

import org.rottweiler488.min.service.ClientConnection;
import org.rottweiler488.min.service.commandHandler.CommandContext;
import org.rottweiler488.min.service.commandHandler.CommandInput;
import org.rottweiler488.min.service.commandHandler.CommandRegistry;

import java.util.List;
import java.util.Scanner;

public class WSClientApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ClientConnection networkClient = new ClientConnection();
        CommandRegistry commandRegistry = new CommandRegistry();
        CommandContext commandContext = new CommandContext(networkClient);

        System.out.println("WELCOME TO THE MESSENGER MIN - CMD");

        //CLIENT LIFE CYCLE
        while (true) {
            String message = scanner.nextLine();

            if(!message.startsWith("/")) {
                List<String> input = List.of("send", message);
                commandRegistry.dispatch(new CommandInput(input, message), commandContext);
            }
            else {
                String argsMessage = message.substring(1);
                List<String> input = List.of(argsMessage.split("\\s+"));
                commandRegistry.dispatch(new CommandInput(input, argsMessage), commandContext);
            }
        }
    }
}
