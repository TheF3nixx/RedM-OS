package System;

public class SystemState {
    private static boolean booted = false;
    private static SystemMode currentMode = SystemMode.NORMAL;
    private static final long BOOT_TIME = System.currentTimeMillis();
    
    public static void setBooted(boolean value){
        booted = value;
    }
    
    public static boolean isBooted(){
        return booted;
    } 
    
    public static void setMode(SystemMode mode){
        currentMode = mode;
    }
    
    public static SystemMode getMode(){
        return currentMode;
    }
    
    public static boolean is(SystemMode mode){
        return currentMode == mode;
    }
     
    public static long getUptime(){
        return (System.currentTimeMillis() - BOOT_TIME)/1000;
    }

    public static String echo(String msg){
        return "[SYSTEM]> " + msg;
    }
    
    //DEPENDE DEL MODO QUE ESTÉ, PARA LOS COMANDOS LUEGO
    public static boolean isRecovery(){
        return currentMode == SystemMode.RECOVERY;
    }
    
    public static boolean isSafe(){
        return currentMode == SystemMode.SAFE;
    }
    
    public static boolean isEmergency(){
        return currentMode == SystemMode.EMERGENCY;
    }
    
    public static boolean isAdmin(){
        return currentMode == SystemMode.ADMIN;
    }
    
    public static boolean isCrashed(){
        return currentMode == SystemMode.CRASHED;
    }
    
    
}
