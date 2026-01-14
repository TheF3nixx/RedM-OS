package Shell.Commands;

import java.util.List;
import java.util.Map;

public interface Command {

    String getName();              //nombre principal
    String getDescription();       //ayuda
    String getUsage();             //cómo se usa

    void execute(List<String> args, Map<String, String> flags);
}

