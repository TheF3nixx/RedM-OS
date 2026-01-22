package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;

import FS.FSManager;
import Managers.ErrorHandler;
import Shell.Commands.Command;

public class CreateEditableFile implements Command{
    @Override
    public String getName() {
        return "write";
    }

    @Override
    public String getDescription() {
        return "Creates an interactive file, which you can edit in real time";
    }

    @Override
    public String getUsage() {
        return "write <filename>";
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {

        if (args.isEmpty()) {
            ErrorHandler.trigger("RM-025", "");
            return;
        }

        FSManager.edit(args.get(0));
    }
}
