package org.rottweiler488.min.service.commandHandler.commands;

import org.rottweiler488.min.service.commandHandler.*;

import java.util.concurrent.CompletableFuture;

public class HelpCommandHandler extends BaseCommandHandler {
    @Override
    public String[] names() {
        return new String[] {"help"};
    }

    @Override
    public CompletableFuture<Void> handle(CommandInput input, CommandContext context) {
        System.out.println("Commands: \n" +
                "/help\n" +
                "/chat [join|exit] <args>");

        return CompletableFuture.completedFuture(null);
    }
}
