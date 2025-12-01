package org.rottweiler488.min.service.commandHandler;

import java.util.concurrent.CompletableFuture;

public interface CommandResponder {
    default CompletableFuture<Void> replyInfo(CommandContext context, String text) {
        System.out.println(text);
        return CompletableFuture.completedFuture(null);
    }

    default CompletableFuture<Void> replyError(CommandContext context, String text) {
        System.out.println(text);
        return CompletableFuture.failedFuture(new Exception());
    }
}
