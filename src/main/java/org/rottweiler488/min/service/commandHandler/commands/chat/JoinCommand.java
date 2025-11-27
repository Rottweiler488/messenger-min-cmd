package org.rottweiler488.min.service.commandHandler.commands.chat;

import org.rottweiler488.min.service.ClientConnection;
import org.rottweiler488.min.service.commandHandler.commands.Command;

import java.util.List;

public class JoinCommand extends Command {
    public JoinCommand() {
        name = "join";
        numberOfArguments = 1;
    }

    @Override
    protected void onExecuteCommand(List<String> input) {
        if (input.isEmpty()) throw new RuntimeException("Join address argument is required.");

        String address = input.get(0);
        ClientConnection.connect(address);
    }
}
