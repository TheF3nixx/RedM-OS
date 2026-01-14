package redmind;

public class SystemState {
    private static boolean emergencyMode = false;
    private static boolean bootedCorrectly = false;
    private static boolean subsystemsActive = false;
    private static boolean unstableBoot = false;
    private static boolean engineeringMode = false;
    private static boolean ghostMode = false;
    private static boolean devMode = false;
    private static boolean extendedMode = false;
    private static boolean commandSystemCorrupted = false;

    // Setters
    public static void setEmergencyMode(boolean state) { emergencyMode = state; }
    public static void setBootedCorrectly(boolean state) { bootedCorrectly = state; }
    public static void setSubsystemsActive(boolean state) { subsystemsActive = state; }
    public static void setUnstableBoot(boolean state) { unstableBoot = state; }
    public static void setEngineeringMode(boolean state) { engineeringMode = state; }
    public static void setGhostMode(boolean state) { ghostMode = state; }
    public static void setDevMode(boolean state) { devMode = state; }
    public static void setCommandSystemCorrupted(boolean state) { commandSystemCorrupted = state; }
    public static void setExtendedMode(boolean state) { extendedMode = state; }
    
    // Getters
    public static boolean isEmergencyMode() { return emergencyMode; }
    public static boolean isBootedCorrectly() { return bootedCorrectly; }
    public static boolean isSubsystemsActive() { return subsystemsActive; }
    public static boolean isUnstableBoot() { return unstableBoot; }
    public static boolean isEngineeringMode() { return engineeringMode; }
    public static boolean isGhostMode() { return ghostMode; }
    public static boolean isDevMode() { return devMode; }
    public static boolean isCommandSystemCorrupted() {return commandSystemCorrupted; }
    public static boolean isExtendedMode() {return extendedMode; }
    
}
