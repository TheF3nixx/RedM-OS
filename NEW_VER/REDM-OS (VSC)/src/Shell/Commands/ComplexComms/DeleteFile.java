package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;

import FS.FSManager;
import Managers.ErrorHandler;
import Shell.Commands.Command;
import System.ErrorC;

public class DeleteFile implements Command{
    @Override
    public String getName() {
        return "dfile";
    }

    @Override
    public String getDescription() {
        return "Sends the target archive to the trash folder, not erasing it completely";
    }

    @Override
    public String getUsage() {
        return "dfile <target>";
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {

        if (args.isEmpty()) {
            ErrorHandler.trigger(ErrorC.MISSING_ARGUMENT, null);
            return;
        }

        FSManager.dfile(args.get(0));
    }
}
