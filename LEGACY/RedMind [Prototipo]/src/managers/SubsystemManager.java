 package managers;

import redmind.SystemState;

public class SubsystemManager {

    private static boolean aiLegacyFramework = false;
    private static boolean neuroLinkAnalytics = false;
    private static boolean passiveBehavioralScan = false;
    private static boolean terminalEchoRecovery = false;
    private static boolean shadowMode = false;

    public static void activateAll() {
        aiLegacyFramework = true;
        neuroLinkAnalytics = true;
        passiveBehavioralScan = true;
        terminalEchoRecovery = true;
        shadowMode = true;
        SystemState.setSubsystemsActive(true);
    }

    // Métodos de apagado individual
    public static void disableAILegacyFramework() {
        aiLegacyFramework = false;
        checkIfAllOff();
    }

    public static void disableNeuroLinkAnalytics() {
        neuroLinkAnalytics = false;
        checkIfAllOff();
    }

    public static void disablePassiveBehavioralScan() {
        passiveBehavioralScan = false;
        checkIfAllOff();
    }

    public static void disableTerminalEchoRecovery() {
        terminalEchoRecovery = false;
        checkIfAllOff();
    }

    public static void disableShadowMode() {
        shadowMode = false;
        checkIfAllOff();
    }

    private static void checkIfAllOff() {
        if (!aiLegacyFramework && !neuroLinkAnalytics &&
            !passiveBehavioralScan && !terminalEchoRecovery && !shadowMode) {
            SystemState.setSubsystemsActive(false);
        }
    }

    // Getters (ya los tienes)
    public static boolean isAILegacyEnabled() { return aiLegacyFramework; }
    public static boolean isNeuroLinkEnabled() { return neuroLinkAnalytics; }
    public static boolean isBehavioralScanEnabled() { return passiveBehavioralScan; }
    public static boolean isEchoRecoveryEnabled() { return terminalEchoRecovery; }
    public static boolean isShadowMode() { return shadowMode; }

}

