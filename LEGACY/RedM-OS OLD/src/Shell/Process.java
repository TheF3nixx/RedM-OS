package Shell;

public class Process {
    private static int nextPID = 1; //Contador global de ID's de procesos
    
    private int pid;
    private String name;
    private boolean running;

    public Process(String name){
        this.pid = nextPID++;
        this.name = name;
        this.running = true;
    }

    public int getPid(){
        return pid;
    }

    public String getName(){
        return name;
    }

    public boolean isRunning(){
        return running;
    }
    
    public void kill(){
        this.running = false;
    }

    @Override
    public String toString() {
        return "[" + pid + "] " + name + " - " + (running ? "RUNNING" : "TERMINATED");
    }
    
    
    
    
    
    
    
    
    
    
}
