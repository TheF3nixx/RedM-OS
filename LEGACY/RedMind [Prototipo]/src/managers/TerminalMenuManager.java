package managers;

import java.util.Scanner;
import functionsMenu.toolsMenu;
import functionsMenu.networkMenu;
import redmind.SystemState;

public class TerminalMenuManager {
    private static final Scanner scanner = new Scanner(System.in);

    public static void startMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== RedMind Terminal Interface Menu ===");
            System.out.println("1. CONFIG");
            System.out.println("2. DIAGNOSTICS");
            System.out.println("3. SECURITY");
            System.out.println("4. NETWORK");
            System.out.println("5. TOOLS");
            System.out.println("6. DEV");
            System.out.println("0. EXIT");
            System.out.print("> Enter option: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": showConfigMenu(); break;
                case "2": showDiagnosticsMenu(); break;
                case "3": showSecurityMenu(); break;
                case "4": showNetworkMenu(); break;
                case "5": showToolsMenu(); break;
                case "6": showDevMenu(); break;
                case "0":
                    System.out.println("Returning to the terminal...");
                    running = false;
                    delay(1000);
                    break;
                default:
                    System.err.println("ERROR: [RM-0012: Invalid menu option]");
            }
        }
    }

    // CONFIG MENU
    private static void showConfigMenu() {
        while (true) {
        System.out.println("\n--- CONFIG ---");
        System.out.println("1. Show system state");
        System.out.println("2. Reboot subsystems");
        System.out.println("3. View system logs");
        System.out.println("0. Return to main menu");
            System.out.print("[config]> ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": System.out.println("Not implemented yet"); break;
                case "2": System.out.println("Not implemented yet"); break;
                case "3": System.out.println("Not implemented yet"); break;
                case "0": return;
                default: System.err.println("ERROR: [RM-0012: Invalid menu option]");
            }
        }
    }

    // DIAGNOSTICS MENU
    private static void showDiagnosticsMenu() {
        while (true) {
        System.out.println("\n--- DIAGNOSTICS ---");
        System.out.println("1. Perform integrity check");
        System.out.println("2. Analyze subsystem latency");
        System.out.println("3. Run full diagnostic");
        System.out.println("0. Return to main menu");
            System.out.print("[diagnostics]> ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": System.out.println("Not implemented yet"); break;
                case "2": System.out.println("Not implemented yet"); break;
                case "3": System.out.println("Not implemented yet"); break;
                case "0": return;
                default: System.err.println("ERROR: [RM-0012: Invalid menu option]");
            }
        }
    }

    // SECURITY MENU
    private static void showSecurityMenu() {
        while (true) {
        System.out.println("\n--- SECURITY ---");
        System.out.println("1. View authentication status");
        System.out.println("2. Lock system");
        System.out.println("3. Wipe login traces");
        System.out.println("0. Return to main menu");
            System.out.print("[security]> ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": System.out.println("Not implemented yet"); break;
                case "2": System.out.println("Not implemented yet"); break;
                case "3": System.out.println("Not implemented yet"); break;
                case "0": return;
                default: System.err.println("ERROR: [RM-0012: Invalid menu option]");
            }
        }
    }

    // NETWORK MENU
    private static boolean broadcastDisconnected = false;

    private static void showNetworkMenu() {
        while (true) {
            System.out.println("\n--- NETWORK ---");
            System.out.println("1. View routing and access map");
            System.out.println("2. Open remote terminal");
            System.out.println("3. Sync with random RedM-OS node");
            System.out.println("4. Download corruption log from network");
            System.out.println("5. Disconnect identity broadcast");
            System.out.println("0. Return to main menu");

            System.out.print("[network]> ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": networkMenu.showAccessMap(); break;
                case "2": networkMenu.openRemoteTerminal(); break; //Ya lo haré
                case "3": networkMenu.syncWithNode(); break; //Ya lo haré
                case "4": networkMenu.downloadCorruptionLog(); break; //Ya lo haré
                case "5": networkMenu.disconnectBroadcast(); break; //Ya lo haré
                case "0": return;
                default: System.err.println("ERROR: [RM-0012: Invalid menu option]");
            }
        }
    }


    // TOOLS MENU
    private static void showToolsMenu() {
        while (true) {
        System.out.println("\n--- TOOLS ---");
        System.out.println("1. Access to traces menu");
        System.out.println("2. ");
        System.out.println("3. ");
        System.out.println("4. ");
        System.out.println("5. ");
        System.out.println("6. Decode hexadecimal string");
        System.out.println("7. Crossfade with parallel timeline");
        System.out.println("0. Return to main menu");
        
            System.out.print("[tools]> ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": toolsMenu.accessTracesMenu();break;
                case "2": System.out.println("Not implemented yet");; break; //Ya lo haré
                case "3": System.out.println("Not implemented yet");; break; //Ya lo haré
                case "4": System.out.println("Not implemented yet");; break; //Ya lo haré
                case "5": System.out.println("Not implemented yet");; break; //Ya lo haré
                case "6": toolsMenu.decodeHexadecimalString(); break; 
                case "7": toolsMenu.crossfadeWithParallelTimeline(); break; //Hacerlo más interesante aún
                case "0": return;
                default: System.err.println("ERROR: [RM-0012: Invalid menu option]");
            }
        }
}

    // DEVELOPER MENU
    private static void showDevMenu() {
        while (true) {  
        System.out.println("\n--- DEV ---");
        System.out.println("1. ");
        System.out.println("2. ");
        System.out.println("3. ");
        System.out.println("4. System flags (advanced)");
        System.out.println("0. Return to main menu");
    
            System.out.print("[debug]> ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    System.out.println("Not implemented yet"); //Ya lo haré
                    break;
                case "2":
                    System.out.println("Not implemented yet"); //Ya lo haré
                    break;
                case "3":
                    System.out.println("Not implemented yet"); //Ya lo haré
                    break;
                case "4":
                    showSystemFlagsMenu();
                    break;
                case "0":
                    return;
                default:
                    System.err.println("ERROR: [RM-0012: Invalid menu option]");
            }
        }
}
    
    private static void showSystemFlagsMenu() {
    while (true) {
        System.out.println("\n-- SYSTEM FLAGS --");
        System.out.println("1. Toggle Emergency Mode (" + SystemState.isEmergencyMode() + ")");
        System.out.println("2. Toggle Dev Mode(" + SystemState.isDevMode() + ")");
        System.out.println("3. Toggle Subsystems (" + SystemState.isSubsystemsActive() + ")");
        System.out.println("4. Toggle Engineering Mode (" + SystemState.isEngineeringMode() + ")");
        System.out.println("5. Toggle Ghost Mode (" + SystemState.isGhostMode() + ")");
        System.out.println("0. Return");
        System.out.print("[flags]> ");
        String input = scanner.nextLine().trim();
        switch (input) {
            case "1": SystemState.setEmergencyMode(!SystemState.isEmergencyMode()); break;
            case "2": SystemState.setDevMode(!SystemState.isDevMode()); break;
            case "3": SystemState.setSubsystemsActive(!SystemState.isSubsystemsActive()); break;
            case "4": SystemState.setEngineeringMode(!SystemState.isEngineeringMode()); break;
            case "5": SystemState.setGhostMode(!SystemState.isGhostMode()); break;
            case "0": return;
            default: System.err.println("ERROR: [RM-0012: Invalid menu option]");
        }
    }
}
    
    private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("INTERRUPT EXCEPTION: System instability detected.");
        }
    }


}
