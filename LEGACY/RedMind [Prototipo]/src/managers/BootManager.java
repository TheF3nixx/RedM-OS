package managers;

public class BootManager {
    public static void bootSequence() {
        delay(2000);
        System.out.println("Booting RedMind Internal System...");
        delay(1000);
        System.out.println("Initializing kernel modules...");
        delay(1200);
        System.out.println("Kernel modules initializated.");
        System.out.println("");
        delay(700);
        System.out.println("Decrypting access layer...");
        delay(1500);
        System.out.println("Decrypted succesfully....");
        System.out.println("");
        delay(700);
        System.out.println("Loading USER_REG protocol...");
        delay(1300);
        System.out.println("Loaded.");
        System.out.println("");
        delay(500);
        System.out.println("Reading config/version.txt...");
        delay(1100);
        System.out.println("All files read, zero errors.");
        System.out.println("");
        delay(500);
        System.out.println("The RedMind Terminal is ready for usage.");
        delay(500);
    }
    
    public static void loadSubsystems() {
        delay(1000);
        System.out.println("[INITIATING SYSTEM BOOT: SECONDARY SUBSYSTEMS]");
        delay(1000);
        System.out.println("> Loading AI legacy frameworks... [M4X v1.03]"); delay(500);
        System.out.println("   → STATUS: OK");

        System.out.println("> Establishing silent routine channels..."); delay(900);
        System.out.println("   → STATUS: OK");

        System.out.println("> Subsystem: NeuroLink Analytics..."); delay(1200);
        System.out.println("   → STATUS: ONLINE");

        System.out.println("> Subsystem: Passive Behavioral Scan..."); delay(1200);
        System.out.println("   → STATUS: ONLINE");

        System.out.println("> Subsystem: Terminal Echo Recovery..."); delay(1200);
        System.out.println("   → STATUS: Partial link established");

        System.out.println("> Subsystem: Containment Interface..."); delay(1200);
        System.out.println("   → STATUS: ERROR");

        System.out.println("> /xbrain/node unhid [access remains locked]"); delay(800);

        System.out.println("> Initiating silent monitoring..."); delay(1200);
        System.out.println("   → STATUS: DONE");

        System.out.println("> Shadow Mode partially enabled. Logs will be suppressed"); delay(1200);
        delay(1000);
        System.out.println("[BOOT COMPLETE: SUBSYSTEMS ACTIVE AND READY FOR USAGE]\n");

        // Aquí es donde se activan "de verdad":
        SubsystemManager.activateAll();
    }

    private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("INTERRUPT EXCEPTION: System instability detected.");
        }
    }
}

