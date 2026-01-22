package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;

import FS.FSManager;
import Managers.ErrorHandler;
import Shell.Commands.Command;

public class DestroyFile implements Command{
     @Override
    public String getName() {
        return "destroy";
    }

    @Override
    public String getDescription() {
        return "Deletes permanently an archive, without confirmation";
    }

    @Override
    public String getUsage() {
        return "destroy <archive>";
    }

    public List<String> getNotes(){
        return List.of(
            "This command is irreversible and does not send the archive to trash"
        );
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {

        if (args.isEmpty()) {
            ErrorHandler.trigger("RM-025", "");
            return;
        }

        FSManager.dtfile(args.get(0));
        
    }
}
