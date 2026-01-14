package Managers;

import java.util.ArrayList;
import Shell.Process;

public class ProcessManager {
    private static ArrayList<Process> processes = new ArrayList<>();
    
    public static Process create(String name){
        Process p = new Process(name);
        processes.add(p);
        return p;
    }
    
    public static boolean kill(int pid){
        for(Process p : processes){
            if(p.getPid() == pid && p.isRunning()){
                p.kill();
                return true;
            }
        }
        return false;
    }
    
    public static ArrayList<Process> list(){        
        return processes;
    }
    
}
