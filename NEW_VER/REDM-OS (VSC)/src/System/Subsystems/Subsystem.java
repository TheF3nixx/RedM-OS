package System.Subsystems;

public interface Subsystem {
    String getName();
    boolean start();
    boolean stop();
    boolean isRunning();
}
