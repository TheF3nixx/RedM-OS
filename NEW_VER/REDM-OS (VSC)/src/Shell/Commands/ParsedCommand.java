package Shell.Commands;

import java.util.List;
import java.util.Map;

public record ParsedCommand(
    String name,
    List<String> args,
    Map<String, String> flags
) {}

