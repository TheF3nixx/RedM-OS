package Shell.Commands.ComplexComms;

import java.util.List;
import java.util.Map;

import Managers.ErrorHandler;
import Shell.Commands.Command;
import System.SystemState;

public class Echo implements Command{

    @Override
    public String getName() {
        return "echo";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public void execute(List<String> args, Map<String, String> flags) {

        if (args.isEmpty()) {
            ErrorHandler.trigger("RM-025", "");
            return;
        }

        IO.IO.output(SystemState.echo(String.join(" ", args)));
    }

}
