package org.rottweiler488.min.service.commandHandler.commands.chat;

import org.rottweiler488.min.service.commandHandler.Command;
import org.rottweiler488.min.service.commandHandler.CommandAction;
import org.rottweiler488.min.service.commandHandler.CommandContext;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ExitCommandAction implements CommandAction, Command {
    @Override
    public String[] names() {
        return new String[] {"exit", "e"};
    }

    @Override
    public CompletableFuture<Void> execute(List<String> args, CommandContext context) {
        context.networkClient().disconnect().thenRun(() ->
                System.out.println("Disconnected.")).exceptionally(throwable -> {
                    System.out.println("Client is already disconnected.");
                    return null;
                });

        return CompletableFuture.completedFuture(null);
    }
}
