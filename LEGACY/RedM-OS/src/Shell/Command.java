package Shell;

import java.util.Arrays;

public class Command {
    public final String name;
    public final String[] flags;
    public final String[] args;

    public Command(String name, String[] flags, String[] args) {
        this.name = name;
        this.flags = flags;
        this.args = args;
    }
    
    public String getName() {
        return name;
    }

    public String[] getFlags() {
        return flags;
    }

    public String[] getArgs() {
        return args;
    }
    
    public static Command parseCommand(String input) {
    // Lógica para analizar texto y extraer nombre, flags y argumentos
    String[] parts = input.trim().split("\\s+");
    String name = parts[0];
    String[] args = Arrays.copyOfRange(parts, 1, parts.length);
    String[] flags = Arrays.stream(args)
                           .filter(a -> a.startsWith("-"))
                           .toArray(String[]::new);
    return new Command(name, flags, args);
}
}
