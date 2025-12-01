package org.rottweiler488.min.service.commandHandler;

import java.util.Map;

public interface SubcommandRegister {
    default <T extends CommandAction & Command> void register(Map<String, CommandAction> subcommands, T commandAction) {
        if (commandAction.names().length == 0) return;

        for (String name : commandAction.names()) {
            subcommands.put(name, commandAction);
        }
    }
}
