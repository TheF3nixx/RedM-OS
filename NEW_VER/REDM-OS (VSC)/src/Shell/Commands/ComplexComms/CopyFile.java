package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;

import FS.FSManager;
import Managers.ErrorHandler;
import Shell.Commands.Command;
import System.ErrorC;

public class CopyFile implements Command{
    @Override
    public String getName() {
        return "rep";
    }

    @Override
    public String getDescription() {
        return "Copies any archive from one point to another";
    }

    @Override
    public String getUsage() {
        return "rep <origin> <target>";
    }

    public List<String> getNotes(){
        return List.of(
            "Supports absolute routes"
        );
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {

        if (args.isEmpty()) {
            ErrorHandler.trigger(ErrorC.MISSING_ARGUMENT, null);
            return;
        }

        FSManager.copy(args.get(0), args.get(1));
        
    }
}
