package Core;
//Se encarga de manejar variables internas del sistema
public class SystemCore {
    private static long systemStartTime;

    public static void initialize() {
        systemStartTime = System.currentTimeMillis();
    }

    public static long getSystemUptime() {
        return System.currentTimeMillis() - systemStartTime;
    }

    public static String getUptimeFormatted() {
        long uptimeMillis = getSystemUptime();
        long seconds = (uptimeMillis / 1000) % 60;
        long minutes = (uptimeMillis / 1000 / 60) % 60;
        long hours = (uptimeMillis / 1000 / 60 / 60);

        return String.format("%02dh %02dm %02ds", hours, minutes, seconds);
    }
    
    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("INTERRUPT EXCEPTION: System instability detected.");
        }
    }
    
    public static void bootSequence(){
        delay(200);
        System.out.println("\u001B[36m[RedM-OS - Secure Shell Interface]\u001B[0m");
        System.out.println("-------------------------------------");

        delay(500);
        System.out.print(">> Initializing kernel modules...     ");
        delay(900);
        System.out.println("\u001B[35m[OK]\u001B[0m");

        delay(300);
        System.out.print(">> Verifying system integrity...      ");
        delay(900);
        System.out.println("\u001B[35m[OK]\u001B[0m");

        delay(300);
        System.out.print(">> Loading user authentication...     ");
        delay(900);
        System.out.println("\u001B[35m[OK]\u001B[0m");

        delay(300);
        System.out.print(">> Checking virtual file system...    ");
        delay(900);
        System.out.println("\u001B[35m[OK]\u001B[0m");

        delay(300);
        System.out.print(">> Parsing internal tags...           ");
        delay(900);
        System.out.println("\u001B[35m[OK]\u001B[0m");

        delay(300);
        System.out.print(">> Syncing memory buffers...          ");
        delay(900);
        System.out.println("\u001B[35m[OK]\u001B[0m");

        delay(300);
        System.out.print(">> Finalizing boot process...         ");
        delay(900);
        System.out.println("\u001B[35m[FINALIZED]\u001B[0m");
        
    }
}
