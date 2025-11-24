package org.rottweiler488.service.commandHandler;

import org.rottweiler488.service.commandHandler.commands.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CommandHandler {
    private Map<String, Command> commands = new HashMap<String, Command>();//CommandsResgister.getCommands();

    public CommandHandler() {
        MinCommand minCommand = new MinCommand();
        commands.put(minCommand.getName(), minCommand);
    }

    public void run(List<String> input) {
        try {
            handleCommand(input);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //TODO: Вместо void передавать Object
    private void handleCommand(List<String> input) {
        if (input == null) throw new RuntimeException("Input cannot be Null.");

        String commandName = input.get(0);
        if (commandName != null && commandName.isBlank()) throw new RuntimeException("Command not found.");

        List<String> nextInput = input.subList(1, input.size());
        Command command = commands.get(commandName);

        if (Objects.isNull(command)) throw new RuntimeException("Command not found.");
        command.executeCommand(nextInput);
    }
}
