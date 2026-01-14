package redmind;

import managers.BootManager;
import managers.CommandManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import otherThings.Connector;

public class RedMind {
    
    public static String loggedUser = null;
    public static String userRole = null;
    public static File currentDirectory = new File(System.getProperty("user.dir"));

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean bootedCorrectly = false;

        if (Connector.getConnection() != null) {
            System.out.println("Connected correctly to RM-Database.");
        } else {
            System.out.println("Could not connect to RM-Database.");
        }
        System.out.println("");
        System.out.println("RedMind Industries - Protocol Terminal");
        System.out.println("Enter a boot code...");
        while (!bootedCorrectly) {
            System.out.print("> ");
            String input = sc.nextLine().trim();
            
            switch (input) {
                case "fastboot":
                    SystemState.setBootedCorrectly(true);
                    SystemState.setDevMode(true);
                    SystemState.setEmergencyMode(true);
                    SystemState.setEngineeringMode(true);
                    SystemState.setSubsystemsActive(true);
                    SystemState.setGhostMode(true);
                    SystemState.setExtendedMode(true);
                    loginPrompt();
                    bootedCorrectly = true;
                    break;
                    
                case "boot -override -protocol ZMX-0921-acc":
                    delay(1000);
                    BootManager.bootSequence();
                    SystemState.setBootedCorrectly(true);
                    System.out.println("NOTE: To use the terminal you must log in with a valid username and password. Command: login [username] [password]");
                    loginPrompt();
                    bootedCorrectly = true;
                    break;

                case "boot -enable -emergencymode":
                    SystemState.setEmergencyMode(true);
                    delay(1000);
                    System.out.println("[!] Emergency Mode enabled.");
                    delay(1000);
                    System.out.println(">> Network lockdown activated.");
                    delay(500);
                    System.out.println(">> All surveillance protocols on standby.");
                    break;

                case "boot -switch subsystems ON":
                    delay(1000);
                    BootManager.loadSubsystems();
                    SystemState.setSubsystemsActive(true);
                    break;

                case "boot -scan -nodeintegrity":
                    delay(1000);
                    System.out.println("> Running node integrity analysis...");
                    delay(1200);
                    System.out.println("> No breaches found.\n");
                    break;

                case "boot -dirtyreboot":
                    System.out.println("[!] WARNING: Dirty reboot initiated...");
                    delay(1500);
                    System.out.println(">> Skipping security checks.");
                    System.out.println(">> Memory cleanup bypassed.");
                    System.out.println(">> Log overwriting in progress..."); delay(1000);
                    System.out.println(">> System is now unstable. Proceed with caution.\n");
                    SystemState.setBootedCorrectly(true);
                    System.out.println("NOTE: To use the terminal you must log in with a valid username and password. Command: login [username] [password]");
                    loginPrompt();
                    SystemState.setUnstableBoot(true);
                    bootedCorrectly = true;
                    break;

                case "boot -adminoverride -engmode":
                    System.out.println("[ENGINEERING MODE ENABLED]");
                    delay(1000);
                    System.out.println(">> Warning: This mode allows critical system manipulation.");
                    delay(500);
                    System.out.println(">> Developer shell: ACTIVE");
                    SystemState.setEngineeringMode(true);
                    break;

                default:
                    if (input.startsWith("boot -override -ghostsession ")) {
                        String ghostUser = input.substring("boot -override -ghostsession ".length());
                        System.out.println("[GHOST SESSION INITIATED]");
                        delay(1200);
                        System.out.println("> Spoofing identity: " + ghostUser);
                        System.out.println("> Creating fake logs..."); delay(700);
                        System.out.println("> Ghost access granted. Session is invisible to monitoring.");
                        SystemState.setGhostMode(true);
                        SystemState.setBootedCorrectly(true);
                        bootedCorrectly = true;
                    } else {
                        System.err.println("ERROR:[RM-0001: Invalid boot command. Please try a valid one]");
                    }
                    break;
            }

                    }
        delay(1000);
        System.out.println("\nType '-help' to view available commands.");
        System.out.println("[REDMIND TERMINAL | V3.2.2]");
        CommandManager.initialize();
        while (true) {
            //System.out.println("[DEBUG] Role detected: " + userRole + ", Access Level: " + getAccessLevel(userRole));
            System.out.print("> ");
            String input = sc.nextLine().trim();
            
            if(input.contains("logout")){
                System.out.println("Closing current session...");
                delay(1000);
                System.out.println("Exiting system...");
                delay(1000);
                System.out.println("System shut correctly.");
                System.exit(0);
            }
            
            CommandManager.execute(input);
          
        }
    }//main
    
//---------------------------------------------------------------------------------------------------------------------------------------------//
     private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("INTERRUPT EXCEPTION: System instability detected.");
        }
    }
//---------------------------------------------------------------------------------------------------------------------------------------------//
     public static String loginPrompt() {
        Scanner scanner = new Scanner(System.in);
        int loginAttempts = 0;
        final int MAX_LOGIN_ATTEMPTS = 3;

        while (loginAttempts < MAX_LOGIN_ATTEMPTS) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] parts = input.split(" "); //Separamos el string del login para que indentifique "usuario" y "contraseña"

            if (parts.length == 3 && parts[0].equals("login")){ //Detectamos que la primera parte es el comando login y hacemos que detecte los dos siguientes espacios como los respectivos usuario y contraseña 
                String username = parts[1];
                String password = parts[2];

                if (credentialsAreValid(username, password)){//Llamamos al método para que compruebe si está en la lista de usuarios registrados
                    String role = getUserRole(username);
                    loggedUser = username;
                    userRole = role; 
                    System.out.println("ACCESS GRANTED. Welcome, " + username + ".");
                    System.out.println("System access level: " + role + ".");
                    return role;
                }else{
                    loginAttempts++;
                    System.err.println("ERROR: [RM-0002: Invalid credentials]");
                }
            }else{
                System.err.println("ERROR: [RM-0003: Invalid command. Use: login [username] [password] ]");
            }
        }

        System.err.println("\nToo many failed attempts. System temporarily locked.");
        return null;
    }
//---------------------------------------------------------------------------------------------------------------------//
    public static boolean credentialsAreValid(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null){ //Leemos el archivo de usuarios
                String[] parts = line.split(":"); //Separamos el string por lo que está separado en el archivo para que lo distinga bien
                if (parts.length >= 3){
                    String storedUsername = parts[0]; //La primera y la segunda parte
                    String storedPassword = parts[1];
                    if (storedUsername.equals(username) && storedPassword.equals(password)){ //Comprobamos que estén en el archivo
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR: [RM-0004: Unable to read user database]");
        }
        return false;
    }
//-------------------------------------------------------------------------------------------------------------------//    
        public static int getRequiredAccessLevel(String path){ //Una función que usaré cuando meta las restricciones de acceso reales
            if (path.contains("confidential")) return 5;
            if (path.contains("classified")) return 4;
            if (path.contains("restricted")) return 3;
            if (path.contains("official")) return 2;
            if (path.contains("freeuse")) return 1;
        return 0;
    }
//---------------------------------------------------------------------------------------------------------------------//
    public static int getAccessLevel(String role){ //Devuelve el nivel de acceso de un usuario específico
        switch (role) {
            case "OPERATOR": return 2;
            case "ADMIN": return 4;
            case "ROOT": return 5;
            default: return 0;
        }
    }
//---------------------------------------------------------------------------------------------------------------------//
  public static String getUserRole(String username){
    try (BufferedReader reader = new BufferedReader(new FileReader("users/users.txt"))){ //Lee el archivo de usuarios
        String line;
        while ((line = reader.readLine()) != null){
            String[] parts = line.split(":"); //Separa por los dos puntos para identificar usuario y contraseña
            if (parts.length >= 3 && parts[0].equals(username)){
                switch (parts[2].toUpperCase()){ //Identifica la última parte y devuelve el rol real
                    case "O": return "OPERATOR";
                    case "A": return "ADMIN";
                    case "R": return "ROOT";
                    default: return "UNKNOWN";
                }   
            }
        }
    } catch (IOException e) {
        System.err.println("ERROR: [RM-0005: Could not read user roles]");
    }
    return null;
}
}
