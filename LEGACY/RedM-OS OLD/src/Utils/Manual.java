package Utils;

import java.util.HashMap;

public class Manual {
    private static final HashMap<String, String> manualMap = new HashMap<>();

    static{//Añadir el resto de comandos
        manualMap.put("login", "Usage: login <username>\nLogs into the system.");
        manualMap.put("logout", "Usage: logout\nLogs out of the current session.");
        manualMap.put("uivs", "Usage: uivs\nDisplays current user integrity and session status.");
        manualMap.put("passby", "Usage: passby <user>\nTemporarily impersonate a user (only if ROOT).");
        manualMap.put("man", "Usage: man <command>\nShows the usage for different commands");
    }

    public static String getManual(String command) {
        return manualMap.getOrDefault(command, "[SYSTEM]> No manual entry for: " + command);
    }
}

