package System.Subsystems;

import IO.IO;

public class Security extends AbstractSubsystem{

    @Override
    public String getName() {
        return "Security";
    }

    @Override
    public void onStart() {
        IO.output("[SECURITY]> Subsystem started");
    }

    @Override
    public void onStop() {
        IO.output("[SECURITY]> Subsystem stopped");
    }
    
}
