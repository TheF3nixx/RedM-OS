package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;

import FS.FSManager;
import Managers.ErrorHandler;
import Shell.Commands.Command;
import System.ErrorC;

public class ViewFile implements Command{
    @Override
    public String getName() {
        return "view";
    }

    @Override
    public String getDescription() {
        return "Views the total content of a file on screen";
    }

    @Override
    public String getUsage() {
        return "view <filename>";
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {

        if (args.isEmpty()) {
            ErrorHandler.trigger(ErrorC.MISSING_ARGUMENT, null);
            return;
        }

        FSManager.view(args.get(0));
    }
}
