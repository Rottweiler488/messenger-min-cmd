package org.rottweiler488.min.service.commandHandler.commands;

import java.util.List;

public class HelpCommand extends Command {
    private final String[] helpPages = {
        "HELP 1",
        "HELP 2",
        "HELP 3"
    };

    public HelpCommand() {
        name = "help";
        //numberOfArguments = 1;
        argumentsImpossible = true;

        //CommandsResgister.addCommand(name, this);
    }

    @Override
    protected void onExecuteCommand(List<String> input) {
        int page = 1;
        if (!input.isEmpty()) {
            try { page = Integer.parseInt(input.get(0)); }
            catch (Exception e) { throw new RuntimeException("Incorrect type of arguments."); }
        }

        String help = getHelp(page);
        System.out.println(help);
    }

    private String getHelp(int page) {
        if (page < 1 || page > helpPages.length) throw new RuntimeException("Page not found.");

        return helpPages[page-1];
    }
}
