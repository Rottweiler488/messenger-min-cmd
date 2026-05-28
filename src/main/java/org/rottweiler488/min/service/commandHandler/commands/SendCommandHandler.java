package org.rottweiler488.min.service.commandHandler.commands;

import org.rottweiler488.min.service.commandHandler.BaseCommandHandler;
import org.rottweiler488.min.service.commandHandler.CommandContext;
import org.rottweiler488.min.service.commandHandler.CommandInput;

import java.util.concurrent.CompletableFuture;

public class SendCommandHandler extends BaseCommandHandler {

    @Override
    public String[] names() {
        return new String[] {"send"};
    }

    @Override
    public CompletableFuture<Void> handle(CommandInput input, CommandContext context) {
        if (input.isEmpty()) return replyError(context, "The message is empty.");
        if (input.args().size() != 1) return replyError(context, "Usage: /send <message>");

        String text = input.args().get(0);
        context.networkClient().sendMessage(text).exceptionally((e) -> {
            System.out.println("There is no connection to the server.");
            return null;
        });

        return CompletableFuture.completedFuture(null);
    }
}
