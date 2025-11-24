package org.rottweiler488.service.commandHandler.commands.chat;

import org.rottweiler488.service.commandHandler.commands.Command;

import java.util.List;

public class JoinCommand extends Command {
    public JoinCommand() {
        numberOfArguments = 1;
    }

    @Override
    protected void onExecuteCommand(List<String> input) {
        if (input.isEmpty()) throw new RuntimeException("Join address argument is required.");


    }
}
