package org.rottweiler488.service.commandHandler.commands;

import java.util.*;

public abstract class Command {
    protected Map<String, Command> commands = new HashMap<String, Command>();

    protected String name = "command";
    protected int numberOfArguments = 0; //Переделать под boolean?
    protected boolean argumentsImpossible = false;

    protected void addCommand(Command command) {
        String commandName = command.getName();
        commands.put(commandName, command);
    }

    protected abstract void onExecuteCommand(List<String> input);

    public void executeCommand(List<String> input) {//, Optional<T> object) {
        int inputSize = input.size();
        //List<String> nextInput = input;//(inputSize > 0 && commands.isEmpty()) ? input.subList(1, inputSize) : input;

        //if (commands.isEmpty())
        if (numberOfArguments > 0 && input.size() < numberOfArguments) throw new RuntimeException("Incorrect number of arguments or wrong command.");

        onExecuteCommand(input);
    }

    protected Command getCommand(List<String> input) {
        if (input.size() <= 0) throw new RuntimeException("Incorrect number of arguments or wrong command. Use '/min help'");
        String commandName = input.get(0);

        Command command = commands.get(commandName);
        if (Objects.isNull(command)) throw new RuntimeException("Command not found.");

        return command;
    }

    public String getName() {
        return name;
    }
}
