package org.rottweiler488.min.service.legacyCommandHandler;

import org.rottweiler488.min.service.legacyCommandHandler.commands.*;

import java.util.HashMap;
import java.util.Map;

public class CommandsResgister { //TODO: Скорее удалить
    private final static Map<String, Command> commands = new HashMap<String, Command>();

    /*public CommandsResgister() {
        Command helpCommand = new HelpCommand();
        commands.put(helpCommand.getName(), helpCommand);

        //...
    }*/

    public static Map<String, Command> getCommands() {
        return commands;
    }

    public static void addCommand(String name, Command command) {
        commands.put(name, command);
    }
}
