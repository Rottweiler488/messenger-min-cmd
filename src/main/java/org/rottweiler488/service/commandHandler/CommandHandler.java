package org.rottweiler488.service.commandHandler;

import org.rottweiler488.service.commandHandler.commands.*;
import org.rottweiler488.service.commandHandler.commands.chat.ChatCommand;

import java.util.*;

public class CommandHandler {
    private Map<String, Command> commands = new HashMap<String, Command>();//CommandsResgister.getCommands();

    public CommandHandler() {
        addCommand(new HelpCommand());
        addCommand(new ChatCommand());
    }

    private void addCommand(Command command) {
        String commandName = command.getName();
        commands.put(commandName, command);
    }

    public void run(List<String> input) {
        try {
            List<String> lowerInput = new ArrayList<>();
            for (String i : input) {
                lowerInput.add(i.toLowerCase());
            }

            handleCommand(lowerInput);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //TODO: Вместо void передавать Object
    private void handleCommand(List<String> input) {
        if (input == null || input.isEmpty()) throw new RuntimeException("Input cannot be empty.");

        String commandName = input.get(0);
        if (commandName != null && commandName.isBlank()) throw new RuntimeException("Command not found.");

        Command command = commands.get(commandName);

        if (Objects.isNull(command)) throw new RuntimeException("Command not found.");
        List<String> nextInput = input.subList(1, input.size());
        command.executeCommand(nextInput);
    }
}
