package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;

import FS.FSManager;
import Managers.ErrorHandler;
import Shell.Commands.*;
import System.ErrorC;

public class Cd implements Command {

    @Override
    public String getName() {
        return "cd";
    }

    @Override
    public String getDescription() {
        return "Change current directory";
    }

    @Override
    public String getUsage() {
        return "cd <path>";
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {

        if (args.isEmpty()) {
            ErrorHandler.trigger(ErrorC.MISSING_ARGUMENT, null);
            return;
        }

        FSManager.cd(args.get(0));
    }

}
