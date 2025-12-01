package org.rottweiler488.min.service.commandHandler;

import java.util.List;

public record CommandInput(List<String> tokens, String raw) {
    public Boolean isEmpty() {return tokens == null || tokens.isEmpty(); }
    public String root() { return isEmpty() ? "" : tokens.get(0); }
    public List<String> args() { return isEmpty() ? java.util.List.of() : tokens.subList(1, tokens.size());}
}
