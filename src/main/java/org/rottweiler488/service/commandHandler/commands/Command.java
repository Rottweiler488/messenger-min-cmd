package org.rottweiler488.service.commandHandler.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Command {
    protected Map<String, Command> commands = new HashMap<String, Command>();

    protected String name = "command";
    protected int numberOfArguments = 0;
    protected boolean argumentsImpossible = false;

//    public Command(String name, int numberOfArguments) {
//        this.name = name;
//        this.numberOfArguments = numberOfArguments;
//    }

    protected abstract void onExecuteCommand(List<String> input);
    //-min- -help- 1
    public void executeCommand(List<String> input) {
        int inputSize = input.size();
        //List<String> nextInput = input;//(inputSize > 0 && commands.isEmpty()) ? input.subList(1, inputSize) : input;

        //if (commands.isEmpty())
        if (numberOfArguments > 0 && input.size() != numberOfArguments) throw new RuntimeException(String.format("Incorrect number of arguments: %s and size %s or wrong command.", numberOfArguments, inputSize));

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
