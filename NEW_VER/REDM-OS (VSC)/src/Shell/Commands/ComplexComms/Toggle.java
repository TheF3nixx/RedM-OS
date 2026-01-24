package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;

import System.ErrorC;
import System.SystemCapabilities;
import System.Toggle.*;
import IO.*;
import Managers.ErrorHandler;

import Shell.Commands.Command;

public class Toggle implements Command{

    @Override
    public String getName() {
        return "toggle";
    }

    @Override
    public String getDescription() {
        return "Enables or disables system elements.";
    }

    @Override
    public String getUsage() {
        return "toggle <category> <name> ON|OFF";
    }

    public List<String> getNotes(){
        return List.of(
            "This command is not well implemented (yet)"
        );
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {
        if(args.size() != 3){
            IO.output("Usage: " + getUsage());
            return;
        }

        String category = args.get(0).toUpperCase();
        String name = args.get(1).toUpperCase();
        String state = args.get(2).toUpperCase();
        //Comprobamos si existe la categoría
        try{
            ToggleCategory.valueOf(category);
        }catch(Exception e){
            ErrorHandler.trigger(ErrorC.CATEGORY_DOES_NOT_EXIST, category);
            return;
        }
        //Comprobamos si existe el nombre
        try{
            SystemCapabilities.valueOf(name);
        }catch(Exception e){
            ErrorHandler.trigger(ErrorC.CAPABILITY_DOES_NOT_EXIST, name);
            return;
        }
        //Activamos o desactivamos según el argumento
        boolean enabled = false;
        if(state.equalsIgnoreCase("ON")) enabled = true;
        else if(state.equalsIgnoreCase("OFF")) enabled = false;
        else{
            ErrorHandler.trigger(ErrorC.STATE_ERROR, state);
            return;
        }
        //Guardamos el estado en el map creando "la llave"
        String key = category.toUpperCase() + "." + name;
        ToggleRegistry.register(key, enabled);
        IO.output("[SYSTEM]> " + name + " set to " + state);
    }

}
