package main;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import IO.*;

public class CommandRegistry {
    private final Map<String, Consumer<Command>> commands = new HashMap<>();

    public void register(String name, Consumer<Command> action) {
        commands.put(name, action);
    }

    public void execute(Command cmd) {
        Consumer<Command> action = commands.get(cmd.name);
        if (action != null) {
            action.accept(cmd);
        } else {
            IO.output("Unknown command: " + cmd.name);
        }
    }
}
