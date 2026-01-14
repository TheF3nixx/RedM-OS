package functionsMenu;

public class networkMenu {
    
    public static void showAccessMap(){
        System.out.println(">> Fetching RedM-OS routing table...");
        delay(800);
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Results: ");
        System.out.println("");
        System.out.println("---ROUTING ACCESS MAP---");
        System.out.println("[LOCAL NODE] 127.0.0.1           — Root access granted");
        System.out.println("[NODE] 192.168.17.6              — S376 (v5)");
        System.out.println("[ALPHA NODE] 192.168.44.3        — S312 (v1)");
        System.out.println("[NODE] 192.168.99.99             — S331 (v3)");
        System.out.println("[NODE] 10.0.2.1                  — Core Proxy (STABLE)");
        System.out.println("");
        System.out.println("---OTHER TERMINALS WITH REDM-OS---");
        System.out.println("Elmore Biotech, n2............... Last Updated:: 2010-05-05");
        System.out.println("Elmore Biotech, n1............... Last Updated:: 2010-04-29");
        System.out.println("Elmore Biotech, n3............... Still active");
        System.out.println("NovaVita......................... Unknown state");
        System.out.println("Cryogenetics..................... Updated 56 mins ago");
        System.out.println("Wellspring Labs.................. Dismanteled 2 years ago");
        System.out.println("");
        System.out.println("SYSTEM NOTE: ALL REDMIND TERMINALS ARE INTERCONNECTED AND THEY DUMP ALL");
        System.out.println("THE INFORMATION THEY RECOMPILE TO THIS SERVER");
        System.out.println("--------------------------------------------------------------------------");
    
    }
    
    public static void openRemoteTerminal(){
    
    
    
    
    
    }
    public static void syncWithNode(){
    
    
    
    
    
    }
    public static void downloadCorruptionLog(){
    
    
    
    
    
    }
    public static void disconnectBroadcast(){
    
    
    
    
    
    
    }
    
    
      private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("INTERRUPT EXCEPTION: System instability detected.");
        }
    }
    
    
    
    
    
    
}
