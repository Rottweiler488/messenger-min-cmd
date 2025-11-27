package org.rottweiler488.min.service.commandHandler.commands.chat;

import org.rottweiler488.min.service.commandHandler.commands.Command;

import java.util.List;

public class ChatCommand extends Command {
    public ChatCommand() {
        name = "chat";
        numberOfArguments = 1;

        addCommand(new JoinCommand());
        addCommand(new ExitCommand());
    }

    @Override
    protected void onExecuteCommand(List<String> input) {
        var command = getCommand(input);

        List<String> nextInput = input.subList(1, input.size());
        command.executeCommand(nextInput);
    }
}