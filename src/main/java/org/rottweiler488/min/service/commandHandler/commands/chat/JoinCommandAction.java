package org.rottweiler488.min.service.commandHandler.commands.chat;

import org.rottweiler488.min.service.commandHandler.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class JoinCommandAction implements CommandAction, CommandResponder, Command {
    @Override
    public String[] names() {
        return new String[] {"join", "j"};
    }

    @Override
    public CompletableFuture<Void> execute(List<String> args, CommandContext context) {
        if (args.isEmpty() || args.size() < 2) return replyError(context, "Usage: /chat join <ip> <port>");

        String ip = args.get(0);
        int port = 0;
        try { port = Integer.parseInt(args.get(1)); }
        catch (NumberFormatException e) { return replyError(context, "Port must be a number."); }

        return context.networkClient().connect(ip, port).thenAccept(ok -> {
            if (ok) System.out.println("Connected to server.");
            else System.out.println("Client is already connected.");
        }).exceptionally(e -> {
            System.out.println("Failed to connect to the server at the specified address.");
            return null;
        });
    }
}
