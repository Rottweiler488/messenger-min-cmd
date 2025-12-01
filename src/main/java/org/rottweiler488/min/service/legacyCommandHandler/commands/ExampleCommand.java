/// Класс ПРИМЕР показывает как создавать свои под-команды/команды.

package org.rottweiler488.min.service.legacyCommandHandler.commands;

import java.util.List;
//import java.util.Optional;

public class ExampleCommand extends Command {//<String> {

    public ExampleCommand() {
        name = "example";
        //numberOfArguments = 0;

        addCommand(new HelpCommand());

        //LEGACY
        //HelpCommand helpCommand = new HelpCommand();
        //commands.put(helpCommand.getName(), helpCommand);
    }

    @Override
    protected void onExecuteCommand(List<String> input) {//, Optional<String> object) {
        var command = getCommand(input);
        //if (Objects.isNull(command)) throw new RuntimeException("Command not found.");

        List<String> nextInput = input.subList(1, input.size());
        command.executeCommand(nextInput);//, object);
    }
}
