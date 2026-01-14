package System;

import IO.IO;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;

public class SystemCrash {

    private static final File CRASH_DIR = new File(SystemPaths.getRealRoot(), "crash");
    private static final File RECOVERY_FILE = new File(CRASH_DIR, "recoveryLog.rc");

    public static void fatal(Throwable error, String message) {

        try {
            //Asegurar directorio
            CRASH_DIR.mkdirs();

            try(BufferedWriter bw = Files.newBufferedWriter(
                    RECOVERY_FILE.toPath(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            )){
                bw.write("=== REDM-OS CRASH LOG ===");
                bw.newLine();
                bw.write("Timestamp: " + LocalDateTime.now());
                bw.newLine();
                bw.write("System mode: " + SystemState.getMode());
                bw.newLine();
                
                if(message != null){
                    bw.write("Message: " + message);
                    bw.newLine();
                }
                
                bw.write("--- Exception ---");
                bw.newLine();
                bw.write(error.toString());
                bw.newLine();
                
                bw.write("--- Stacktrace ---");
                bw.newLine();
                for(StackTraceElement el : error.getStackTrace()){
                    bw.write("  at " + el.toString());
                    bw.newLine();
                }
            }
        }
        catch(IOException ex){
            IO.error("[DEVELOPER]> How tf could this fail?");
        }

        SystemState.setMode(SystemMode.CRASHED);

        IO.fatal("\n[ERROR] System halted due to fatal exception.");
        IO.fatal("Reboot required. Crash log generated.");
        IO.fatal("Entering safe mode on next start.");

        System.exit(1);
    }
}
