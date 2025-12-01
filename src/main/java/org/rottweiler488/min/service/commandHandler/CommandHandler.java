package org.rottweiler488.min.service.commandHandler;

import java.util.concurrent.CompletableFuture;

public interface CommandHandler {
    CompletableFuture<Void> handle(CommandInput input, CommandContext context);
}
