package System;

import java.util.EnumMap;

public class SystemState {
    private static boolean booted = false;
    private static SystemMode currentMode = SystemMode.NORMAL;
    private static final long BOOT_TIME = System.currentTimeMillis();
    private static final EnumMap<SystemCapabilities, Boolean> capability = new EnumMap<>(SystemCapabilities.class);
    
    //BOOT
    public static void setBooted(boolean value){
        booted = value;
    }
    
    public static boolean isBooted(){
        return booted;
    } 
    
    //MODO
    public static void setMode(SystemMode mode){
        currentMode = mode;
    }
    
    public static SystemMode getMode(){
        return currentMode;
    }
    
    public static boolean is(SystemMode mode){
        return currentMode == mode;
    }
    
    
    //DEPENDE DEL MODO QUE ESTÉ
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

    //MISC DE ESTADO
    public static long getUptime(){
        return (System.currentTimeMillis() - BOOT_TIME)/1000;
    }

    public static String echo(String msg){
        return "[SYSTEM]> " + msg;
    }

    //CAPACIDADES DEL SISTEMA
    public static boolean enableAll(){
        for(SystemCapabilities cap : SystemCapabilities.values()){
            capability.put(cap, true);
        }
        return true;
    }

    public static boolean disableAll(){
        for(SystemCapabilities cap : SystemCapabilities.values()){
            capability.put(cap, false);
        }
        return true;
    }

    public static boolean hasCapability(SystemCapabilities c){
        return capability.get(c);
    }

    public static boolean enableCapability(SystemCapabilities c){
        capability.put(c, true);
        return true;
    }

    public static boolean disableCapability(SystemCapabilities c){
        capability.put(c, false);
        return true;
    }

}
