package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;

import FS.FSManager;
import Managers.ErrorHandler;
import Shell.Commands.Command;
import System.ErrorC;

public class RestoreFile implements Command{
     @Override
    public String getName() {
        return "restore";
    }

    @Override
    public String getDescription() {
        return "If an archive is in the trash bin, this command restores it to its original position";
    }

    @Override
    public String getUsage() {
        return "restore <archive>";
    }

    public List<String> getNotes(){
        return List.of(
            "To do this, you must be INSIDE the trash bin, on the phisical files part, otherwise it will not work."
        );
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {

        if (args.isEmpty()) {
            ErrorHandler.trigger(ErrorC.MISSING_ARGUMENT, null);
            return;
        }

        FSManager.rfile(args.get(0));
    }
}
