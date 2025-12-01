package org.rottweiler488.min.service.commandHandler;

import org.rottweiler488.min.service.commandHandler.commands.HelpCommandHandler;
import org.rottweiler488.min.service.commandHandler.commands.SendCommandHandler;
import org.rottweiler488.min.service.commandHandler.commands.chat.ChatCommandHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CommandRegistry implements CommandResponder {
    private final Map<String, CommandHandler> commands = new HashMap<>();

    public CommandRegistry() {
        register(new ChatCommandHandler());
        register(new HelpCommandHandler());
        register(new SendCommandHandler());
    }

    public <T extends Command & CommandHandler> void register(T commandHandler) {
        commands.put(commandHandler.names()[0], commandHandler);
    }

    public CompletableFuture<Void> dispatch(CommandInput input, CommandContext context) {
        if (input.isEmpty()) return CompletableFuture.completedFuture(null);

        var command = commands.get(input.root().toLowerCase());
        if (command == null) {
            return replyError(context, "Unknown command: " + input.root());
        }

        return command.handle(input, context);
    }
}
