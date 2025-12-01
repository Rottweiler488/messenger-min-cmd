package org.rottweiler488.min.service.commandHandler;

import java.util.concurrent.CompletableFuture;
import java.util.List;

@FunctionalInterface
public interface CommandAction {
    CompletableFuture<Void> execute(List<String> args, CommandContext context);
}
