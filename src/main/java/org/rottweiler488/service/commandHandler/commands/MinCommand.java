package org.rottweiler488.service.commandHandler.commands;

import java.util.List;
import java.util.Objects;

public class MinCommand extends Command {

    public MinCommand() {
        name = "min";
        //numberOfArguments = 0;

        HelpCommand helpCommand = new HelpCommand();
        commands.put(helpCommand.getName(), helpCommand);
    }

    @Override
    protected void onExecuteCommand(List<String> input) {
        Command command = getCommand(input);
        if (Objects.isNull(command)) throw new RuntimeException("Command not found.");

        List<String> nextInput = input.subList(1, input.size());
        command.executeCommand(nextInput);
    }
}
