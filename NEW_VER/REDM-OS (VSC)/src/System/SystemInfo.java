package System;

import IO.IO;

public class SystemInfo {
    private static final String SYSTEM_NAME = "RedM-OS";
    private static String version = "1.0.0";
    private static final String ARCH = "RVM-64";

    public static String getName(){
        return SYSTEM_NAME;
    }
    
    public static String getArch(){
        return ARCH;
    }
    
    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        SystemInfo.version = version;
    }
    
    public static String summary(){
        return SYSTEM_NAME + " v" + version + "[" + ARCH + "]";
    }
    
    public static void getAllInfo(){
        IO.loadingBar(20, 90);
        IO.output("========== [SYSTEM INFORMATION] ==========");
        IO.output(summary());
        IO.output("> Developer Organization: RedMind Industries");
        IO.output("> Division: Technology & Informatics Department, Erebos Division");
        IO.output("> Project Directors:");
        IO.output("             • Dr. Oliver Johanson");
        IO.output("             • Dr. Klaus Christiansenn");
        IO.output("> Project Codename: RedMOS");
        IO.output("> Primary Objective: Develop a self-sustaining system");
        IO.output("                     capable of operating under high-entropy or unstable environments.");
        IO.output("> Core Language: Java");
        IO.output("> Pseudo-language: MindScript");
        IO.output("> System Birth Date: 12/02/2005");
        IO.output("> Under supervision of the Overseer Network (Status: disabled)");
        IO.output("> System Integrity: STABLE");
            IO.output("========== [KERNEL INFORMATION] ==========");
        IO.output("Kernel Identifier: RMK-9s33fA01b");
        IO.output("Kernel Build: IMKOS/ß-1.5");
        IO.output("Integrity Check: OK");
            IO.output("Memory Cluster Link: stable");
        IO.output("Thread Synchronization: active");
        IO.output("QuantumLink: online");
        IO.output("==============================================");
    }
    
    
}
