package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;
import IO.*;
import Managers.CommandManager;
import Managers.ErrorHandler;
import Shell.Commands.Command;

public class HandBook implements Command{

    @Override
    public String getName() {
        return "hb";
    }

    @Override
    public String getDescription() {
        return "Provides a detailed report of a command";
    }

    @Override
    public String getUsage() {
        return "hb <command>";
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {
        if(args.isEmpty()){
            IO.output("[HB]> Available commands:");
            for (String name : CommandManager.getAllCommandNames()) {
                IO.output("  " + name);
            }
            return;
        }

        String target = args.get(0);
        
        //Primero comprobamos si es un comando simple
        if(CommandManager.isSimpleCommand(target)){
            IO.output("[HB]> Command: " + target);
            IO.output("Description: No additional documentation available.");
            return;
        }

        //Si no es un comando simple, es uno complejo
        Command cmd = CommandManager.getComplexCommand(target);
        if(cmd == null){
            ErrorHandler.trigger("024", target);
            return;
        }

        IO.output("[HB]> Command: " + cmd.getName());
        IO.output("Description: " + cmd.getDescription());
        IO.output("Usage: " + cmd.getUsage());
        //Si tiene flags
        if(!cmd.getFlags().isEmpty()){
            IO.output("Flags:");
            cmd.getFlags().forEach((k, v) ->
                IO.output("  " + k + "  " + v)
            );
        }
        //Si tiene notas extra
        if (!cmd.getNotes().isEmpty()) {
            IO.output("Notes:");
            cmd.getNotes().forEach(n ->
                IO.output("  " + n)
            );
        }


    }

}
