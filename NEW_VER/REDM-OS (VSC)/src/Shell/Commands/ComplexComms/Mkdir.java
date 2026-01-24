package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;

import FS.FSManager;
import Managers.ErrorHandler;
import Shell.Commands.Command;
import System.ErrorC;

public class Mkdir implements Command{

   @Override
    public String getName() {
        return "mkdir";
    }

    @Override
    public String getDescription() {
        return "Creates a directory";
    }

    @Override
    public String getUsage() {
        return "mkdir <dirName>";
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {

        if (args.isEmpty()) {
            ErrorHandler.trigger(ErrorC.MISSING_ARGUMENT, null);
            return;
        }

        FSManager.mkdir(args.get(0));
    }

}
