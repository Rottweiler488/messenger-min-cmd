package org.rottweiler488.min.service.commandHandler.commands.chat;

import org.rottweiler488.min.service.commandHandler.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ChatCommandHandler extends BaseCommandHandler implements SubcommandRegister {
    private final Map<String, CommandAction> subcommands = new HashMap<>();

    public ChatCommandHandler() {
        register(subcommands, new JoinCommandAction());
        //JoinCommandAction joinCommandAction = new JoinCommandAction();
        //subcommands.put(joinCommandAction.name(), joinCommandAction);
        //subcommands.put("j", subcommands.get(joinCommandAction.name())); //ПЕРЕДЕЛАТЬ

        register(subcommands, new ExitCommandAction());
        //ExitCommandAction exitCommandAction = new ExitCommandAction();
        //subcommands.put(exitCommandAction.name(), exitCommandAction);
        //subcommands.put("e", subcommands.get(exitCommandAction.name()));
    }

    @Override
    public String[] names() {
        return new String[] {"chat"};
    }

    @Override
    public CompletableFuture<Void> handle(CommandInput input, CommandContext context) {
        List<String> args = input.args();

        return dispatchSubcommands(args, subcommands, context);
    }
}
