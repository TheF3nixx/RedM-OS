package System;

import IO.IO;
import Managers.ErrorHandler;
import System.Subsystems.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class BootLoader{
    private static final File RECOVERY_FILE = new File(SystemPaths.getRealRoot(), "crash/recoveryLog.rc");
    
    private static void ensureRootDir(){
        File root = SystemPaths.getRealRoot();
        if(!root.exists()){
            root.mkdirs();
        }
    }
    
    private static void delay(long ms){
        try{Thread.sleep(ms);}catch(InterruptedException e){}
    }
    
    public static void boot(BootMode mode){
        ensureRootDir();
        IO.pulseLoader("Initializing program...", 30, 100);
        IO.output(">>Booting RedM-OS (" + mode + " MODE)");
        switch(mode){
            case NORMAL:
                normalBoot();
                break;
                
            case SAFE:
               safeBoot();
                break;

            case EMERGENCY:
                emergencyBoot();
                break;

            case ADMIN:
                adminBoot();
                break;

            case RECOVERY:
                recoveryBoot();
                break;
                
            case SUBSYSTEMS:
                subsystemsBoot();
                break;
        }
        
        SystemState.setBooted(true);
        IO.output(">>Boot complete. Active system mode: " + SystemState.getMode());
    }
    
    public static void normalBoot() {
        IO.output("---NORMAL BOOT MODE---");
        delay(400);

        IO.output("[NORMAL] Boot sequence initiated.");
        delay(500);

        IO.output("[NORMAL] Checking system modules...");
        IO.loadingBar(25, 400);
        delay(500);

        File root = SystemPaths.getRealRoot();
        if (!root.exists() || root.listFiles().length == 0) {
            IO.output("[NORMAL] Root directory missing or empty. Recreating structure...");
            IO.loadingBar(30, 400);
            root.mkdirs();
            SystemPaths.ensureSystemStructure();
            delay(500);
        }
        IO.output("[NORMAL] Root directory verified: " + root.getAbsolutePath());

        IO.output("[NORMAL] Loading standard services...");
        String[] modules = {"KernelCore", "FileSystem", "UserManager", "Logger"};
        for (String m : modules) {
            IO.output("  > Initializing " + m + "...");
            IO.loadingBar(15, 200);
            delay(300);
        }

        IO.output("[NORMAL] Standard services online.");
        delay(500);

        IO.output("[NORMAL] System ready for normal operations.");
        delay(700);

        SystemState.setMode(SystemMode.NORMAL);
    }

    public static void safeBoot() {
        IO.output("---SAFE BOOT MODE---");
        delay(400);

        IO.output("[SAFE] Boot sequence initiated in safe mode.");
        delay(500);

        IO.output("[SAFE] Checking filesystem and critical services...");
        IO.loadingBar(30, 400);
        delay(500);

        File root = SystemPaths.getRealRoot();
        if (!root.exists() || root.listFiles().length == 0) {
            IO.output("[SAFE] Root directory missing. Recreating structure...");
            IO.loadingBar(25, 400);
            root.mkdirs();
            SystemPaths.ensureSystemStructure();
            delay(500);
        }
        IO.output("[SAFE] Root directory verified: " + root.getAbsolutePath());

        IO.output("[SAFE] Loading minimal critical modules...");
        String[] modules = {"KernelCore", "Logger"};
        for (String m : modules) {
            IO.output("  > Initializing " + m + "...");
            IO.loadingBar(10, 200);
            delay(250);
        }

        IO.output("[SAFE] Minimal services online.");
        delay(500);

        IO.output("[SAFE] Only essential commands available. Restricted access enforced.");
        delay(700);

        SystemState.setMode(SystemMode.SAFE);
    }

    public static void emergencyBoot() {
        IO.output("---EMERGENCY BOOT MODE---");
        delay(400);

        IO.output("[EMERGENCY] Boot sequence initiated due to critical system failure.");
        delay(500);

        IO.output("[EMERGENCY] Checking system integrity...");
        IO.loadingBar(25, 400);
        delay(500);

        File root = SystemPaths.getRealRoot();
        if (!root.exists() || root.listFiles().length == 0) {
            IO.output("[EMERGENCY] Root directory missing. Recreating structure...");
            IO.loadingBar(20, 400);
            root.mkdirs();
            SystemPaths.ensureSystemStructure();
            delay(500);
        }
        IO.output("[EMERGENCY] Root directory verified: " + root.getAbsolutePath());

        IO.output("[EMERGENCY] Loading only emergency modules...");
        String[] modules = {"KernelCore", "CrashHandler", "Logger"};
        for (String m : modules) {
            IO.output("  > Initializing " + m + "...");
            IO.loadingBar(10, 200);
            delay(300);
        }

        IO.output("[EMERGENCY] Emergency services online. Minimal operations only.");
        delay(500);

        IO.output("[EMERGENCY] Critical system mode active. Use recovery procedures if needed.");
        delay(700);

        SystemState.setMode(SystemMode.EMERGENCY);
    }
    
    public static void adminBoot() {
        IO.output("---ADMIN BOOT MODE---");
        delay(500);

        IO.output("[ADMIN] Boot sequence initiated for privileged mode.");
        delay(600);

        IO.output("[ADMIN] Checking critical system modules...");
        IO.loadingBar(30, 400);
        delay(500);

        // Comprobar estructura básica del sistema
        File root = SystemPaths.getRealRoot();
        if (!root.exists() || root.listFiles().length == 0) {
            IO.output("[ADMIN] Root directory missing or empty. Recreating structure...");
            IO.loadingBar(30, 400);
            root.mkdirs();
            SystemPaths.ensureSystemStructure();
            delay(500);
        }
        IO.output("[ADMIN] Root directory verified: " + root.getAbsolutePath());

        // Cargando módulos críticos de administración
        IO.output("[ADMIN] Loading privileged modules...");
        String[] modules = {"KernelSupervisor", "SecurityManager", "UserController", "ResourceMonitor", "SafeLock"};
        for (String m : modules) {
            IO.output("  > Initializing " + m + "...");
            IO.loadingBar(15, 200);
            delay(300);
        }

        IO.output("[ADMIN] Verifying configuration files...");
        IO.loadingBar(25, 300);
        delay(400);

        IO.output("[ADMIN] Privileged services online.");
        delay(500);

        SystemState.setMode(SystemMode.ADMIN);

        //Nota para el usuario real
        IO.output("[NOTICE] Access to privileged commands will require ADMIN credentials upon login.");
    }

    public static void recoveryBoot() {
        IO.output("---SYSTEM RECOVERY MODE---");
        delay(500);
        IO.output("[RECOVERY] System entered recovery mode.");
        delay(700);

        IO.output("[RECOVERY] Checking integrity...");
        IO.loadingBar(20, 500);
        delay(600);

        // --- COMPROBACIÓN / RECREACIÓN DEL ROOT ---
        File root = SystemPaths.getRealRoot();

        if (!root.exists() || root.listFiles().length == 0) {
            IO.output("[RECOVERY] Root directory missing or empty. Recreating structure...");
            IO.loadingBar(20, 500);
            root.mkdirs();
            SystemPaths.ensureSystemStructure();
            delay(400);
        }

        IO.output("[RECOVERY] Verifying root directory: " + root.getAbsolutePath());

        // --- CRASHLOG ---
        if (RECOVERY_FILE.exists()) {
            IO.output("[RECOVERY] Crash log detected: " + RECOVERY_FILE.getName());
            delay(600);

            try{
                IO.output("[RECOVERY] Reading crash report...");
                delay(500);

                List<String> lines = Files.readAllLines(RECOVERY_FILE.toPath());
                for (String l : lines) IO.output("[CRASHLOG] " + l);

            }catch(IOException e){
                ErrorHandler.trigger("005", "recoveryLog.rc");
            }

            delay(500);
            IO.output("[RECOVERY] Clearing crash marker...");
            IO.loadingBar(20, 40);
            RECOVERY_FILE.delete();
            delay(800);

        } else {
            IO.output("[RECOVERY] No crash log found. (Strange)");
            delay(400);
        }
        
        IO.output("[RECOVERY] Loading environment variables...");
        IO.loadingBar(20, 500);

        IO.output("[RECOVERY] Minimal services restored.");
        delay(700);

        SystemState.setMode(SystemMode.RECOVERY);
    }

    public static void subsystemsBoot(){//hacer
        SubsystemManager.register(new Security());
        SubsystemManager.register(new Scheduler());

        SubsystemManager.start("Security");
        SubsystemManager.start("Scheduler");
    }



    
}
