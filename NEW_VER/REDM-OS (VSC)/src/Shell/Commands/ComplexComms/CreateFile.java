package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;

import FS.FSManager;
import Managers.ErrorHandler;
import Shell.Commands.Command;

public class CreateFile implements Command{
    @Override
    public String getName() {
        return "cfile";
    }

    @Override
    public String getDescription() {
        return "Creates an empty file";
    }

    @Override
    public String getUsage() {
        return "cfile <filename> <extension>";
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {

        if (args.isEmpty()) {
            ErrorHandler.trigger("RM-025", "");
            return;
        }

        FSManager.cfile(args.get(0), args.get(1));
    }
}
