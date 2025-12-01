package org.rottweiler488.min.service.commandHandler;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class BaseCommandHandler implements CommandHandler, CommandResponder, Command {
    protected CompletableFuture<Void> dispatchSubcommands(List<String> args, Map<String, CommandAction> handlers, CommandContext context) {
        if (args == null || args.isEmpty()) return replyError(context, "Usage: see /help");

        String sub = args.get(0).toLowerCase(Locale.ROOT);
        var action = handlers.get(sub);

        if (action == null) return replyError(context, "Unknown subcommand: " + sub);

        return action.execute(args.subList(1, args.size()), context);
    }
}
