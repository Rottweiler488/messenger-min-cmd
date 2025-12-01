package org.rottweiler488.min.service.legacyCommandHandler.commands.chat;

import org.rottweiler488.min.service.ClientConnection;
import org.rottweiler488.min.service.legacyCommandHandler.commands.Command;

import java.util.List;

public class ExitCommand extends Command {
    public ExitCommand() {
        name = "exit";
    }

    @Override
    protected void onExecuteCommand(List<String> input) {
        //ClientConnection.disconnect();
    }
}
