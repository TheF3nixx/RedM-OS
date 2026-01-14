package System.Subsystems;

import java.util.HashMap;
import java.util.Map;
import IO.IO;

public class SubsystemManager {
    private static final Map<String, Subsystem> subsystems = new HashMap<>();

    public static void register(Subsystem s){
        subsystems.put(s.getName(), s);
    }

    public static boolean start(String name){
        Subsystem s = subsystems.get(name);
        return s != null && s.start();
    }

    public static boolean stop(String name){
        Subsystem s = subsystems.get(name);
        return s != null && s.stop();
    }

    public static Subsystem get(String name){
        return subsystems.get(name);
    }

    public static void list(){
        subsystems.values().forEach(s -> {
            IO.output("[" + s.getName() + "] " +
                      (s.isRunning() ? "RUNNING" : "STOPPED"));
        });
    }

}
