package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;

import FS.FSManager;
import Managers.ErrorHandler;
import Shell.Commands.Command;

public class Rmdir implements Command{
    @Override
    public String getName() {
        return "rmdir";
    }

    @Override
    public String getDescription() {
        return "Deletes a directory";
    }

    @Override
    public String getUsage() {
        return "rmdir <dirName>";
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {

        if (args.isEmpty()) {
            ErrorHandler.trigger("RM-025", "");
            return;
        }

        FSManager.rmdir(args.get(0));
    }
}
