package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;

import FS.FSManager;
import Managers.ErrorHandler;
import Shell.Commands.Command;

public class MoveFile implements Command{
    @Override
    public String getName() {
        return "shift";
    }

    @Override
    public String getDescription() {
        return "Moves one archive to another position";
    }

    @Override
    public String getUsage() {
        return "shift <origin> <target>";
    }

    public List<String> getNotes(){
        return List.of(
            "Pretty much like the 'rep' command..."
        );
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {

        if (args.isEmpty()) {
            ErrorHandler.trigger("RM-025", "");
            return;
        }

        FSManager.move(args.get(0), args.get(1));
    }
}
