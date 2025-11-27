package org.rottweiler488.service.commandHandler.commands.chat;

import org.rottweiler488.service.ClientConnection;
import org.rottweiler488.service.commandHandler.commands.Command;

import java.util.List;

public class ExitCommand extends Command {
    public ExitCommand() {
        name = "exit";
    }

    @Override
    protected void onExecuteCommand(List<String> input) {
        ClientConnection.disconnect();
    }
}
