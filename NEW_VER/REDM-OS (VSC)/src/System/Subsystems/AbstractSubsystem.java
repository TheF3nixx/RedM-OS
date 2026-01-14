package System.Subsystems;

public abstract class AbstractSubsystem implements Subsystem {

    protected boolean running = false;

    @Override
    public boolean start() {
        if (running) return false;
        running = true;
        onStart();
        return true;
    }

    @Override
    public boolean stop() {
        if (!running) return false;
        running = false;
        onStop();
        return true;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    protected abstract void onStart();
    protected abstract void onStop();
}
