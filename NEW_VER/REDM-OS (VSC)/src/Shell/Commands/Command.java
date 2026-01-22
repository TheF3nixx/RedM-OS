package Shell.Commands;

import java.util.List;
import java.util.Map;

public interface Command {

    String getName();              //nombre principal
    String getDescription();       //ayuda
    String getUsage();             //cómo se usa

    default Map<String, String> getFlags(){ //opcional, indica todas las posibles flags del comando
        return Map.of();
    }

    default List<String> getNotes(){ //opcional, por si hay notas adicionales que añadir
        return List.of();
    }

    void execute(List<String> args, Map<String, String> flags);
}

