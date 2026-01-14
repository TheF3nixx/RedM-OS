package Shell.Commands;

import java.util.*;

public class CommandParser {

    public static ParsedCommand parse(String input) {
        String[] tokens = input.trim().split("\\s+");

        String name = tokens[0];
        List<String> args = new ArrayList<>();
        Map<String, String> flags = new HashMap<>();

        for (int i = 1; i < tokens.length; i++) {
            String t = tokens[i];

            if (t.startsWith("--")) {
                String[] p = t.substring(2).split("=", 2);
                flags.put(p[0], p.length == 2 ? p[1] : "true");
            }
            else if (t.startsWith("-")) {
                flags.put(t.substring(1), "true");
            }
            else {
                args.add(t);
            }
        }

        return new ParsedCommand(name, args, flags);
    }
}

