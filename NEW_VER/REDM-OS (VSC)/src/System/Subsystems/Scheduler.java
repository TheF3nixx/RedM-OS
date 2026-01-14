package System.Subsystems;

import IO.IO;

public class Scheduler extends AbstractSubsystem{

    private long ticks = 0;

    @Override
    public String getName() {
        return "Scheduler";
    }

    @Override
    public void onStart() {
        IO.output("[SCHEDULER]> Subsystem started");
    }

    @Override
    public void onStop() {
        IO.output("[SCHEDULER]> Subsystem stopped");
    }

    public void tick(){
        if (!running) return;
        ticks++;
        if (ticks % 10 == 0)
            IO.output("[SCHEDULER]> Tick " + ticks);
    }

    public long getTicks(){
        return ticks;
    }
    

}
