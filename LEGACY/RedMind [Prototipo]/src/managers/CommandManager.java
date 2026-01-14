package managers;

import otherThings.FailureSim;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import redmind.RedMind;
import redmind.SystemState;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import files.BinaryFile;
import otherThings.UserData;

//---------------------------------------------------------------------------------------------------------------------------------------------------------//

public class CommandManager {
    private static Map<String, Runnable> simpleCommands = new HashMap<>();
    private static Map<String, Runnable> complexCommands = new HashMap<>();
    public static File currentDirectory = new File(System.getProperty("user.dir"));
    private static String[] currentInputParts; // Para almacenar temporalmente el input
    private static long bootTime = System.currentTimeMillis();
    private static final Map<String, Long> lockedFiles = new HashMap<>();
    private static File passFile = new File("confidential/core/passwords.txt"); // donde están las contraseñas
    private static Scanner sc = new Scanner(System.in);
    public static HashSet<Integer> lockedNodes = new HashSet<>();
    private static File USERS_FILE = new File("users/users.txt");
    private static final Map<String, String[]> users = new HashMap<>();
    
    /*PLANTILLAS PARA COMANDOS
     simpleCommands.put("", () -> {});
    
        complexCommands.put("", () -> {
            if (currentInputParts.length < 3) {
                System.out.println("Usage: ");
                return;
            }
            function(currentInputParts[1], currentInputParts[2]);
        });
    
    */
    
    public static void initialize() {        
    simpleCommands.put("-help", () -> {
        System.out.println("------------------------ COMMON COMMANDS ------------------------");
        System.out.println("version");
        System.out.println("whoami");
        System.out.println("listdir");
        System.out.println("clear");
        System.out.println("date");
        System.out.println("pwd");
        System.out.println("status-report");
        System.out.println("uptime");
        System.out.println("motd");
        System.out.println("quote");
        System.out.println("accessMenu");
        System.out.println("system-modes");
        System.out.println("cd [directory]");
        System.out.println("view [file]");
        System.out.println("echo [text]");
        System.out.println("diagram [file]");
        System.out.println("");
        System.out.println("logout");
    
    if (SystemState.isSubsystemsActive()) {
        System.out.println("\n---------------------- SUBSYSTEMS COMMANDS ----------------------");
        System.out.println("copy [file] [destiny]");
        System.out.println("delete [file/directory]"); //wip
        System.out.println("move [file] [destiny]"); //wip
        System.out.println("type [file]"); //wip
        System.out.println("restart-subsystems"); //wip
        System.out.println("toggle-firewall"); //wip
        System.out.println("scan-ports"); 
        System.out.println("recompile [module]");//wip
    }

    if (SystemState.isEngineeringMode()) {
        System.out.println("\n---------------------- ENGINEERING COMMANDS ----------------------");
        System.out.println("dump-memory"); //wip
        System.out.println("reboot-kernel"); //wip
        System.out.println("overclock -unsafe"); //wip
        System.out.println("run-loopback-test"); 
        System.out.println("execute [protocol]");
        System.out.println("reboot-kernel"); //wip
        System.out.println("set-protocol [name]"); //wip
    }

    if (SystemState.isEmergencyMode()) {
        System.out.println("\n---------------------- EMERGENCY COMMANDS ------------------------");
        System.out.println("shutdown-f");
        System.out.println("repair-mode"); //wip
        System.out.println("destroy-RedMind");
    }

    if (SystemState.isGhostMode()) {
        System.out.println("\n------------------------ GHOST COMMANDS --------------------------");
        System.out.println("ghost-log"); //wip
        System.out.println("trace-user -silent"); //wip
        System.out.println("invis-ping"); //wip
        System.out.println("fragment-user"); //wip
    }

    if (SystemState.isDevMode()) {
        System.out.println("\n------------------------- DEVELOPER COMMANDS -------------------------");
        System.out.println("simulate-failure"); 
        System.out.println("log-dump"); //wip
        System.out.println("sys-trace"); //wip
        System.out.println("nodeMatrix"); 
        System.out.println("load-nodes [action]"); //wip
        System.out.println("manageUsers"); // --> Mirar que no se pueda escribir dos veces la misma contraseña
        System.out.println("launch-ui [page]"); 
        System.out.println("reveal-core"); //wip
        System.out.println("accessDatabase"); //wip
        System.out.println("trace-channel"); //wip
    } 
    
    if (SystemState.isExtendedMode()) {
        System.out.println("\n-------------INTERACTIVE MODULES & COMPLEX COMMANDS -------------------");
        System.out.println("[DOCUMENTS >> BINARY DOCUMENTS]");
        System.out.println("doc [file] [action]");
        System.out.println(""); //todos a partir de aquí están como ideas, ni siquiera en wip
        System.out.println("[PROCESSES]");
        System.out.println("process"); 
        System.out.println("proc-kill"); 
        System.out.println("");
        System.out.println("[USERS]");
        System.out.println("set-pass"); 
        System.out.println("");
        System.out.println("[TOOLS]");
        System.out.println("ping [node]");
        System.out.println("path [origin] [destiny]");
        System.out.println("search [module/file] [text]");
        System.out.println("");
        System.out.println("[METACOMMANDS]");
        System.out.println("format r:");
        System.out.println("cls");
        System.out.println("ver");
        System.out.println("");
        System.out.println("[TOKENS]");
        System.out.println("p-available");
        System.out.println("p-add [token(s)] [route]");
        System.out.println("p-rm [token(s)] [route]");
        System.out.println("p-l [route]");
        System.out.println("p-grant [token(s)] [user] [route]");
        System.out.println("");
        System.out.println("[GROUPS]");
        System.out.println("group info [name]");
        System.out.println("group create [name]");
        System.out.println("group delete [name]");
        System.out.println("group list");
        System.out.println("");
        System.out.println("[DIRECTORIES]");
        System.out.println("mkdir [directory]");
        System.out.println("dldir [directory]");
        System.out.println("");
        System.out.println("[USERS]");
        System.out.println("user-c [name] [password]");
        System.out.println("user-r [name]");
        System.out.println("passby [user]");
        System.out.println("loginas [user]");
        System.out.println("who");
        System.out.println("");
        System.out.println("[ARCHIVES]");
        System.out.println("mkfile [name/route]");
        System.out.println("dlfile [name/route]");
        System.out.println("");
        System.out.println("[BACKUP]");
        System.out.println("backup-create");
        
    }

    System.out.println("------------------------------------------------------------------");
    System.out.println("TIP: Some commands appear only in selected system modes.");
});

//----------------------------------------------------------------------------------------------------------------//
//-----------------------------------------------COMANDOS COMUNES-------------------------------------------------//

        simpleCommands.put("version", () -> {
            System.out.println("RedMind Terminal | Version 3.1.7 ----- 03/22/2013");
            System.out.println("RedM-OS              | Version 5.0.1 ----- 11/19/20--");
        });
    
        simpleCommands.put("whoami", () -> {
            if (RedMind.loggedUser != null) {
                System.out.println("Logged user: " + RedMind.loggedUser);
            } else {
                System.err.println("ERROR: [RM-0007: No user logged in.]");
            }
        });
            
        simpleCommands.put("listdir", () -> {
            listDir(currentDirectory, null);
        });
        
        simpleCommands.put("clear", () -> {
            for (int i = 0; i < 50; i++) System.out.println();
        });
        
        simpleCommands.put("date", () -> {
            System.out.println("Current system date: " + new Date());
        });
        
        simpleCommands.put("pwd", () -> {
            System.out.println("Current path: " + currentDirectory.getAbsolutePath());
        });
        
        simpleCommands.put("status-report", () -> {
            statusReport();
        });
        
        simpleCommands.put("uptime", () -> {
            uptime();
        });
        
        simpleCommands.put("motd", () -> {
            motd();
        });
        
        simpleCommands.put("quote", () -> {
            quote();
        });
        
        simpleCommands.put("accessMenu", () -> {
            System.out.println("Accessing general menu...");
            delay(1000);
            TerminalMenuManager.startMainMenu();
        });

        simpleCommands.put("system-modes", () -> {
            System.out.println("System Modes:");
            System.out.println(" > Emergency Mode: " + SystemState.isEmergencyMode());
            System.out.println(" > Developer Mode: " + SystemState.isDevMode());
            System.out.println(" > Subsystems Active: " + SystemState.isSubsystemsActive());
            System.out.println(" > Engineering Mode: " + SystemState.isEngineeringMode());
            System.out.println(" > Ghost Mode: " + SystemState.isGhostMode());
            System.out.println("> Extended Mode: " + SystemState.isExtendedMode());
        });
        
         complexCommands.put("cd", () -> {
            if (currentInputParts.length < 2) {
                System.out.println("Usage: cd [directory]");
                return;
            }
            changeDirectory(currentInputParts[1]);
        });
        
        complexCommands.put("view", () -> {
            if (currentInputParts.length < 2) {
                System.out.println("Usage: view [file]");
                return;
            }
            viewFile(currentInputParts[1]);
        });
        
        complexCommands.put("echo", () -> {
            if (currentInputParts.length < 2) {
                System.out.println("Usage: echo [text]");
                return;
            }
            String text = currentInputParts[1];
            System.out.println("[SYSTEM]> " + text);
        });
        
        complexCommands.put("diagram", () -> {
            if (currentInputParts.length < 2) {
                System.out.println("Usage: diagram [file]");
                return;
            }
            openHtmlGraph(currentInputParts[1]);
        });

//----------------------------------------------------------------------------------------------------------------//
//-----------------------------------------------COMANDOS SUBSYST-------------------------------------------------//

    complexCommands.put("copy", () -> {
        if(!SystemState.isSubsystemsActive()){
            System.err.println("ACCESS DENIED. Only available with subsystems enabled.");
        }else{
            if (currentInputParts.length < 3) {
                    System.out.println("Usage: copy [file] [destiny]");
                    System.out.println("You must use absolute routes ([dir]/[subdir]/[...]/[file]");
                    return;
            }
            copyFile2(currentInputParts[1], currentInputParts[2]);
        }
    });
    
    simpleCommands.put("scan-ports", () -> {
        if(!SystemState.isSubsystemsActive()){
            System.err.println("ACCESS DENIED. Only available with subsystems enabled.");
        }else{
            openHtmlPage("scan_ports");
        }
    });


//----------------------------------------------------------------------------------------------------------------//
//-----------------------------------------------COMANDOS INGNIER-------------------------------------------------//

        simpleCommands.put("run-loopback-test", () -> {
            if (!SystemState.isEngineeringMode()) {
                System.err.println("ERROR: [RM-0022: ACCESS DENIED. Only available in Engineering Mode]");
            }else{
                delay(1000);
                Random time = new Random();
                String type;
                int num = time.nextInt(1000);
                if(num < 400){
                   type = "FAST";
                }else{
                   type = "SLOW; Contact your nearest RedMind Kernel Administrator";
                }

                System.out.println("Kernel response time: " + num + " ms (" + type + ")");
            }
        });
        
        complexCommands.put("execute", () -> {
            if (!SystemState.isEngineeringMode()) {
                System.err.println("ERROR: [RM-0022: ACCESS DENIED. Only available in Engineering Mode]");
            }else{
                if (currentInputParts.length < 2) {
                    System.out.println("Usage: execute [protocol-name]");
                    return;
                }
                executeProtocol(currentInputParts[1]);
            }
        });

//----------------------------------------------------------------------------------------------------------------//
//-----------------------------------------------COMANDOS EMERGEN-------------------------------------------------//

    simpleCommands.put("destroy-RedMind", () -> {
            if (!SystemState.isEmergencyMode()) {
                System.err.println("ERROR: [RM-0022: ACCESS DENIED. Only available in Emergency Mode]");
            }else{
                destroyRedMind();
            }
    });
       simpleCommands.put("shutdown-f", () -> {
           if (!SystemState.isEmergencyMode()) {
                System.err.println("ERROR: [RM-0022: ACCESS DENIED. Only available in Emergency Mode]");
            }else{
                System.err.println("WARNING: This action might break the system");
                System.out.println("Do you want to continue? (y/n)");
                String inp = sc.nextLine();
                if(!inp.equalsIgnoreCase("y")){
                    System.out.println("Operation cancelled.");
                    return;
                }
                System.out.println("Shutting down system...");
                delay(2000);
                System.out.println("");
                System.exit(0);
           }
       });

//----------------------------------------------------------------------------------------------------------------//
//-----------------------------------------------COMANDOS FANTASM-------------------------------------------------//



//----------------------------------------------------------------------------------------------------------------//
//-----------------------------------------------COMANDOS DESARRO-------------------------------------------------//
        
         simpleCommands.put("nodeMatrix", () -> {
            if(!SystemState.isDevMode()){
                System.err.println("ERROR: [RM-0022: ACCESS DENIED. Only available in Developer Mode]");
                return;
            }
            nodeMatrix();
        });
         
         simpleCommands.put("accessDatabase", () -> {
            if(!SystemState.isDevMode()){
                System.err.println("ERROR: [RM-0022: ACCESS DENIED. Only available in Developer Mode]");
                return;
            }
            DatabaseManager.start();
        });
        
        simpleCommands.put("manageUsers", () -> {
            if(!SystemState.isDevMode()){
                System.err.println("ERROR: [RM-0022: ACCESS DENIED. Only available in Developer Mode]");
                return;
            }
            manageUsers();
        });
        
        complexCommands.put("launch-ui", () -> {
            if(!SystemState.isDevMode()){
                System.err.println("ERROR: [RM-0022: ACCESS DENIED. Only available in Developer Mode]");
                return;
            }
            if (currentInputParts.length < 2) {
                System.out.println("Usage: launch-ui [pageName]");
                return;
            }
            openHtmlPage(currentInputParts[1]);
        });
        
        simpleCommands.put("simulate-failure", () -> {
             if(!SystemState.isDevMode()){
                System.err.println("ERROR: [RM-0022: ACCESS DENIED. Only available in Developer Mode]");
                return;
            }
            FailureSim.totalFailure();
        });
        
//----------------------------------------------------------------------------------------------------------------//
//-----------------------------------------------COMANDOS EXTEND-------------------------------------------------//
    complexCommands.put("doc", () -> {
            if(!SystemState.isExtendedMode()){
                System.err.println("ERROR: [RM-0022: ACCESS DENIED. Only available in Extended Mode]");
                return;
            }
            if (currentInputParts.length < 2) {
                System.out.println("Usage: doc [filename] [command]");
                return;
            }
        try {
            binaryFileManager(currentInputParts[1], currentInputParts[2]);
        } catch (IOException ex) {
            //terminar
        } catch (ClassNotFoundException ex) {
            //terminar
        }
        });


    }//initialize
//--------------------------------------------------------------------------------------------------//   
    // Ejecutar comandos
    public static void execute(String input) {
        currentInputParts = input.trim().split(" ");
        String command = currentInputParts[0];
        if (simpleCommands.containsKey(command)) {
            simpleCommands.get(command).run();
        } else if (complexCommands.containsKey(command)) {
            complexCommands.get(command).run();
        } else {
            System.err.println("ERROR: [RM-0008: Unrecognized command.]");
        }
}

//--------------------------------------------------------------------------------------------------//   
    private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("INTERRUPT EXCEPTION: System instability detected.");
        }
    }
//--------------------------------------------------------------------------------------------------//   
    // Cambiar de directorio
    public static void changeDirectory(String dirName) {
        if (dirName.equals("..")) {
            File parent = currentDirectory.getParentFile();
            if (parent != null) {
                currentDirectory = parent;
                System.out.println("Moved to: " + currentDirectory.getPath());
            } else {
                System.out.println("Already at root.");
            }
            return;
        }

        File target = new File(currentDirectory, dirName);
        if (target.exists() && target.isDirectory()) {
            currentDirectory = target;
            System.out.println("Moved to: " + currentDirectory.getPath());
        } else {
            System.err.println("ERROR: [RM-0009: Directory " + dirName + " doesn't exist. " );
        }
    }

    public static String getCurrentPath() {
        return currentDirectory.getPath();
    }
    
//--------------------------------------------------------------------------------------------------//
    //Listar directorios    
    public static void listDir(File currentDirectory, String subfolderName) {
    File targetDir;

    if (subfolderName == null) {
        // Listar el contenido del directorio actual
        targetDir = currentDirectory;
    } else {
        // Listar el contenido de una subcarpeta (si existe)
        targetDir = new File(currentDirectory, subfolderName);
        if (!targetDir.exists() || !targetDir.isDirectory()) {
            System.err.println("ERROR: [RM-0010: Subdirectory not found or is not a directory.]");
            return;
        }
    }

    File[] contents = targetDir.listFiles();
    if (contents == null || contents.length == 0) {
        System.out.println("[Empty directory]");
        return;
    }

    for (File f : contents) {
        System.out.println((f.isDirectory() ? "[DIR] " : "[FILE] ") + f.getName());
    }
}
//----------------------------------------------------------------------------------------------------//  
    //Visualizar documentos sin/con contraseña y archivos sql
    /*
        FLUJO:
        1. Lee la primera línea, si detecta que es #PROTECTED, se va a la segunda
        2. En la segunda leería algo como #KEY=[COD]
        3. Debería leer el archivo de contraseñas hasta que de con la clave
        4. Una vez que de la clave, pide la contraseña y esta tiene que coincidir con la KEY
        5. Si falla tres veces, se sale y el archivo se bloquea por 5 minutos
    */
     public static void viewFile(String fileName){
    File fileToView = new File(currentDirectory, fileName);
    //Primero nos aseguramos de que el archivo existe y no es un directorio, y que no es un archivo .bin
    if(!fileToView.exists() || fileToView.isDirectory()){
        System.err.println("ERROR: [RM-0011: File not found or is a directory]");
        return;
    }
    
    if (fileName.endsWith(".bin")) {
        System.err.println("ERROR: [RM-0039: Cannot view binary files with 'view'. Use 'doc [file] read' instead.]");
        return;
    }
    
    //Luego leemos el Map de lockedFiles para ver si el archivo está bloqueado
    if(lockedFiles.containsKey(fileName)){
        long unlockTime = lockedFiles.get(fileName);
        if (System.currentTimeMillis() < unlockTime) {
            System.err.println("ACCESS DENIED: File temporarily locked due to repeated failed attempts. Try again later.");
            return;
        } else {
            // El tiempo ya pasó, lo desbloqueamos
            lockedFiles.remove(fileName);
        }
    }
    //Ahora empezamos a leer el archivo, debemos leer las dos primeras líneas
    try (BufferedReader br = new BufferedReader(new FileReader(fileToView))) {
        String firstLine = br.readLine(); 
        if(firstLine.equals("#PROTECTED")){//Leemos la primera línea y comparamos. Si es así, iniciamos el proceso de contraseña, si no, pues leemos normalmente
            String keyLine = br.readLine();
            if(keyLine == null || !keyLine.startsWith("#KEY=")){//Comprobamos que la key no es nula o que no empiece por #KEY
                System.err.println("ERROR: [RM-0013: Protected file without valid KEY]");
                return;
            }

            String key = keyLine.substring(5).trim(); //Esto hace que cuente los 5 primeros espacios en esa línea y que lea a partir de lo que es el código de verdad (#KEY= (5) a61 (el resto))
            String requiredPassword = null; //Variable temporal donde se almacena la contraseña requerida

            try (BufferedReader passReader = new BufferedReader(new FileReader(passFile))) {//Leemos el archivo de contraseñas
                String passEntry;
                while((passEntry = passReader.readLine()) != null) {//Lee línea por línea
                    String[] parts = passEntry.split("=", 2);//Con esto separamos clave de contraseña
                    if (parts.length == 2 && parts[0].trim().equals(key)) { //Preguntamos si la key es igual a la contraseña requerida
                        requiredPassword = parts[1].trim();
                        break;
                    }
                }
            }

            if (requiredPassword == null) {
                System.err.println("ERROR: [RM-0014: No password found for key " + key + "]");
                return;
            }
            //Ahora que hemos detectado la contraseña, debemos pedírsela al usuario para que la introduzca
            int atts = 0;
            System.out.println("This is a protected file.");
            while(atts < 3){
                System.out.println("Enter password for file " + fileName + ": ");
                String input = sc.nextLine().trim();
                if (input.equals(requiredPassword)){ //Preguntamos si la contraseña es igual a la ya detectada
                    System.out.println("Access granted");
                    delay(1000);
                    System.out.println("File correctly accessed.");
                    delay(1000);

                    
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                        break;
                    } else {
                    System.err.println("ERROR: [RM-0015: Incorrect password]");
                    System.out.println("[" + (2 - atts) + " attempts left]");
                    atts++;
                }
            }

            if(atts >= 3){ //Si has hecho ya los 3 intentos, se bloquea por 5 minutos el archivo
                lockedFiles.put(fileName, System.currentTimeMillis() + 5 * 60 * 1000);
                System.err.println("FILE LOCKED: Too many failed attempts. Please wait 5 minutes.");
            }
        } else {//Si no está protegido, simplemente hacemos que lea el archivo
                System.out.println(firstLine);
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
    } catch (IOException ex) {
        System.err.println("ERROR: [RM-0006: Error reading the file]");
    }
}
    
//--------------------------------------------------------------------------------------------------// 
    //Hacer el reporte del estado del sistema, aleatorio
    public static void statusReport() {
        Random rand = new Random();

        System.out.println("[SYSTEM STATUS REPORT]");

        System.out.print("CPU: ");
        delay(800);
        System.out.println(rand.nextInt(100) < 80 ? "STABLE" : "UNSTABLE"); // 80% estable

        System.out.println("");

        System.out.print("Memory usage: ");
        delay(1000);
        System.out.println(rand.nextInt(100) < 65 ? "STABLE" : "OVERLOAD"); // 65% estable

        System.out.println("");

        System.out.print("Memory Load: ");
        delay(800);
        int memLoad = 50 + rand.nextInt(40); // entre 50% y 89%
        System.out.println(memLoad + "%");

        System.out.println("");

        System.out.print("Neural network: ");
        delay(900);
        System.out.println(rand.nextInt(100) < 40 ? "LINKED" : "DESYNCHRONIZED"); // 40% que esté bien

        System.out.println("");

        System.out.print("Threat level: ");
        delay(1500);
        String[] threatLevels = {"GREEN", "YELLOW", "ORANGE", "RED"};
        String threat = threatLevels[rand.nextInt(100) < 60 ? 0 : rand.nextInt(threatLevels.length)];
        System.out.println(threat);

        System.out.println("");
        delay(1000);
        System.out.println("[STATUS REPORT COMPLETED]");
        System.out.println("");
    
}   
//----------------------------------------------------------------------------------------------------------//
    public static void uptime() {
        long now = System.currentTimeMillis();
        long uptime = now - bootTime;

        long seconds = (uptime / 1000) % 60;
        long minutes = (uptime / (1000 * 60)) % 60;
        long hours = (uptime / (1000 * 60 * 60));

        System.out.printf("Uptime: %02dh %02dm %02ds\n", hours, minutes, seconds);

    }
//----------------------------------------------------------------------------------------------------------//
    public static void motd() {
        String[] messages = {
                "RedMind OS welcomes you. Mind the loops.",
                "Reminder: Reality forks at Layer 3.",
                "Subject 011 left a note. It was blank.",
                "Echoes are not errors.",
                "Everything is fine. Probably.",
                "You are not supposed to be here.",
                "Error 0x00: No error."
            };
            int random = new Random().nextInt(messages.length);
            System.out.println("[MOTD] -> " + messages[random]);
    }
//----------------------------------------------------------------------------------------------------------//
    public static void quote(){
        String[] quotes = {
        "\"Those who stare into the void should blink.\"",
        "\"A system with secrets is never idle.\"",
        "\"Do not trust logs written in silence.\"",
        "\"You are the anomaly.\"",
        "\"Truth is only visible in debug mode.\""
    };
    System.out.println(quotes[new Random().nextInt(quotes.length)]);
    }
//----------------------------------------------------------------------------------------------------------//
    public static void nodeMatrix(){
        System.out.println("---NODE MATRIX---");
        int rows = 6;
        int cols = 5;
        //Para marcar los nodos usados, usamos un hashset y los almacenaremos ahí (es una variable global
        //Ahora para mostrar la matriz, hacemos dos for iterados
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                int value = i * cols + j + 1; //Esto hace el total. Ej: 1*0+0+1 = 1 (fila 0, columna 0)
                if(lockedNodes.contains(value)){
                    System.out.print("X  ");
                }else{
                    System.out.printf("%-3d ", value); //%-3d es una cláusula de valor fijo de anchura
                }
            }
            System.out.println();
        }
        int max = rows * cols;
        //Preguntamos qué nodo desea elegir
        System.out.println("Select node (or write 'release [numberNode]' to release the node):"); String select = sc.nextLine();
        if (select.startsWith("release")){//[HACER] Tengo que comprobar que si un nodo no está ocupado no se pueda usar "release" en él
        try{
            int num = Integer.parseInt(select.split(" ")[1]);//Esto coge el número
            if (num <= 0 || num > max){//Para que no se pase de índice
                System.err.println("ERROR: [RM-0019: Out of bounds]");
                return;
            }
            lockedNodes.remove(num);//Quita el bloqueo de la lista de bloqueados
            System.out.println("Node " + num + " released.");
        }catch (Exception e){
            System.err.println("ERROR: [RM-0020: Invalid release syntax]");
        }
    }else{
        try{
            int selected = Integer.parseInt(select);//Se asegura que sea un entero
            if (selected <= 0 || selected > max){//Comprueba que no se salga de índice
                System.err.println("ERROR: [RM-0019: Out of bounds]");
                return;
            }
            if (!lockedNodes.contains(selected)){//Si no está bloqueado, se selecciona y te manda al submenú de nodos
                lockedNodes.add(selected);
                System.out.println("");
                nodeActions(selected);//Accede al submenú
            } else {
                System.err.println("ERROR: [RM-0018: Node already locked]");
            }
        } catch (NumberFormatException e) {
            System.err.println("ERROR: [RM-0021: Invalid input, expected a number or 'release N']");
        }
    }
    }

    private static void nodeActions(int selected){
        while(true){
            System.out.println("---NODE " + selected + "---");
            System.out.println("NOTE: None of the options are available at the moment."); //Ya los haré
            System.out.println("1. Analyze node integrity");
            System.out.println("2. Extract information");
            System.out.println("3. Program inyection");
            System.out.println("0. Return to main menu");
            int opc = sc.nextInt();
            switch(opc){
                case 1: break;
                case 2: break;
                case 3: break;
                case 0: 
                    System.out.println("Returning to main menu...");
                    delay(800);
                    System.out.println("Changes saved.");
                    System.out.println("");
                    return;
                default: System.err.println("ERROR: [RM-0012: Invalid menu option]"); break;
            }
        }
    
    }
//------------------------------------------------------------------------------------------------------------------------------------//
//ESTA ES LA SECCIÓN DE USUARIOS
    private static void manageUsers(){
        if(!SystemState.isDevMode()){
            System.err.println("ERROR: [RM-0022: ACCESS DENIED. Only available in Developer Mode.]");
        }
        loadUsers();
        while(true){
        System.out.println("---USERS MANAGEMENT MENU---");
        System.out.println("1. View"); //Funciona
        System.out.println("2. Add"); //Funciona
        System.out.println("3. Delete"); //Ya lo haré
        System.out.println("4. Edit"); //Ya lo haré
        System.out.println("0. Return to main menu");
        int opc = sc.nextInt();
        sc.nextLine();
   
        switch(opc){
            case 1: viewUsers(); break;
            case 2: addUser(); break;
            case 0: 
                System.out.println("Returning to main menu...");
                delay(800);
                System.out.println("Changes saved.");
                System.out.println("");
                return;
            default: System.err.println("Not implemented yet."); break;
        }
    }
        
    }

    private static void loadUsers(){
        try(BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))){
            String line; 
            while((line = br.readLine()) != null){
                String parts[] = line.split(" ");
                if(parts.length == 3){
                    users.put(parts[0], new String[]{parts[1], parts[2]});
                }
            }
            delay(1000);
            System.out.println("Users loaded correctly.");
            delay(800);
        }catch(IOException e){
            System.err.println("ERROR: [RM-0006: Error reading the file]");
        }
    
    }
    
    private static void saveUserToFile(String us, String pass, String acc){
        try(FileWriter fw = new FileWriter(USERS_FILE, true)){
            fw.write(us + ":" + pass + ":" + acc + "\n");
        }catch(IOException e){
            System.err.println("ERROR: [RM-0023: Could not save user]");
        }
    }

    private static void viewUsers(){
        try(BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))){
            String line; 
            while((line = br.readLine()) != null){
                String parts[] = line.split(" ");
                System.out.println("User:password:level --->    " + parts[0]);
            }
        }catch(IOException e){
            System.err.println("ERROR: [RM-0004: Unable to read user database]");
        }
    }

    private static void addUser(){
        System.out.println("---CREATE NEW USER---");
        System.out.print("Username: ");
        String username = sc.nextLine();
        if (users.containsKey(username)) {
            System.err.println("ERROR: [RM-0024: Already exists an user with that name]");
            return;
        }
        
        if (!username.matches("[a-zA-Z0-9]{4,9}")) {
           System.err.println("ERROR: [RM-0025: Invalid name. Use letters/numbers only (4-9 characters). No spaces]");
           return;
        }
        
       System.out.print("Password: ");
       String password = sc.nextLine();
       
       if (!password.matches("(?=.*[a-z])(?=.*\\d)[a-z\\d]{17}")) {
            System.err.println("ERROR: [RM-0026: Password must have 17 characters with lowercase and numbers]");
            return;
        }

        System.out.print("Access level [O/A/R]: ");
        String level = sc.nextLine().toUpperCase();

        if (!level.matches("[OAR]")) {
            System.err.println("ERROR: [RM-0027: Invalid access level]");
             return;
        }

        users.put(username, new String[]{password, level});
        saveUserToFile(username, password, level);
        System.out.println("User '" + username + "' correctly added.");

    }
    
//-----------------------------------------------------------------------------------------------------------------------------//
    
    private static void copyFile2(String sourcePath, String destinationPath) {
        //Creamos dos ficheros: el que queremos mover y de destino
        File sourceFile = new File(sourcePath);
        File destinationFile = new File(destinationPath);
        //Comprobamos si el archivo no existe o es un directorio
        if (!sourceFile.exists() || sourceFile.isDirectory()) {
            System.err.println("ERROR: [RM-0011: File not found or is a directory]");
            return;
        }   

        try {
            //Coje el archivo padre del destino y comprueba si es nulo o no
            File parent = destinationFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs(); // Solo actúa si hay una ruta real de carpetas
            }
            //Usamos la función copy integrada en java
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archive '" + sourceFile.getName() + "' copied correctly to '" + destinationPath + "'");
        } catch (IOException e) {
            System.err.println("ERROR: [RM-0028: Archive could not be copied]");
            System.err.println("Details: " + e.getMessage());
    }
}
    
//----------------------------------------------------------------------------------------------------------------------------//
//ABRIR INTERFACES
    public static void openHtmlPage(String pageName) {
    try {
        File htmlFile = new File("restricted/interfaces/" + pageName + ".html"); //Indicamos la ruta de la página
        if (!htmlFile.exists()) { //Comprobamos si existe
            System.err.println("ERROR: [RM-0029: HTML page not found]");
            return;
        }
        Desktop.getDesktop().browse(htmlFile.toURI());//Comando interno de java para redirigir la salida a la página HTML y la abrimos
        System.out.println(pageName + " successfully launched.");
    } catch (IOException e) {
        System.err.println("ERROR: [RM-0030: Unable to open HTML page]");
        e.printStackTrace();
    }
}
    
//----------------------------------------------------------------------------------------------------------------------------//
//PROTOCOLOS (ARCHIVOS .BAT)
    public static void executeProtocol(String protocolName) {
    try {
        String filePath = "restricted/protocols/" + protocolName + ".bat";
        File file = new File(filePath);

        if (!file.exists()) {
            System.err.println("ERROR: [RM-0031: Protocol not found]");
            return;
        }

        // Ejecutar en ventana separada
        new ProcessBuilder("cmd", "/c", "start", "", file.getAbsolutePath())
                .inheritIO()
                .start();

        System.out.println("> Executing protocol: " + protocolName + "...");
    } catch (IOException e) {
        System.err.println("ERROR: [RM-0032: Could not execute protocol]");
        e.printStackTrace();
    }
}

//---------------------------------------------------------------------------------------------------------------------------//
//ABRIR DIAGRAMAS/GRÁFICOS
    public static void openHtmlGraph(String pageName) {
    try {
        File htmlFile = new File("restricted/diagrams/" + pageName + ".html"); //Indicamos la ruta de la página
        if (!htmlFile.exists()) { //Comprobamos si existe
            System.err.println("ERROR: [RM-0029: HTML page not found]");
            return;
        }
        Desktop.getDesktop().browse(htmlFile.toURI());//Comando interno de java para redirigir la salida a la página HTML y la abrimos
        System.out.println(pageName + " successfully launched.");
    } catch (IOException e) {
        System.err.println("ERROR: [RM-0030: Unable to open HTML page]");
        e.printStackTrace();
    }
}
    
//------------------------------------------------------------------------------------------------------//
//DESTROY REDMIND
public static void destroyRedMind(){
    System.out.println("Are you completely sure you want to do this? (Y/N)");
                String an = sc.nextLine().trim();
                if(an.equals("Y") || an.equals("y")){
                    System.out.println("WARNING: This action is irreversible.");
                    delay(5000);
                    System.out.println("STARTING...");
                    delay(5000);
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            System.err.println("DESTROYING CORE FILES...");
                            delay(2000);
                            System.err.println("SYSTEM CRITICAL: REDMIND CORE UNRESPONSIVE");
                            delay(4000);
                            System.err.println("ALL FILES DELETED");
                            delay(2000);
                            System.err.println("ALL PROCEDURES, EXPERIMENTS AND DOCUMENTS ERASED");
                            delay(3500);
                            System.err.println("BLOCKING AND SHUTTING DOWN REDMIND FOREVER...");
                            delay(3000);
                            System.out.println("Successfully erased RedMind. Good Bye.");
                            System.exit(0);
                        }
                    }, 5000);
            } 
}
//--------------------------------------------------------------------------------------------------------------------//
//MANEJAR ARCHIVOS BINARIOS (LEER, ESCRIBIR, EDITAR)
public static void binaryFileManager(String fileName, String mode) throws IOException, ClassNotFoundException {
    if (!fileName.endsWith(".bin") && !fileName.endsWith(".txt")) {
        fileName += ".bin";
    }

    String fullPath;
    
     if (fileName.contains("/") || fileName.contains("\\")) {
        fullPath = fileName;
    } else {
        // Si solo pone el nombre, usamos internal por defecto
        fullPath = "classified/internal/" + fileName;
    }

    switch (mode) {
        case "write":
            if (fileName.endsWith(".bin")) {
                UserData user = new UserData("Klaus", "Developer");
                BinaryFile file = new BinaryFile(fullPath);
                file.save(user);
                System.out.println("[SYSTEM]> File wrote correctly.");
            } else {
                System.err.println("ERROR: [RM-0041: 'write' is not allowed on text files]");
            }
            break;

        case "read":
            if (fileName.endsWith(".bin")) {
                BinaryFile file = new BinaryFile(fullPath);
                Object obj = file.read();
                if (obj != null) {
                    System.out.println("[SYSTEM]> Content:");
                    System.out.println(obj);
                }
            } else {
                viewFile(fileName); // reutilizamos lector de texto
            }
            break;

        case "edit":
            if (fileName.endsWith(".txt")) {
                editTextFile(fullPath);
            } else if (fileName.endsWith(".bin")) {
                editBinaryFile(fullPath);
            } else {
                System.err.println("ERROR: [RM-0040: Unknown extension for editing]");
            }
            break;

        default:
            System.err.println("ERROR: [RM-0043: Unknown mode '" + mode + "']");
    }
}

//-------------------------------------------------------------------------------------------------------------------------------------------//
public static void editTextFile(String fullPath) throws IOException {//Sí que funciona, solo que hay que meter rutas absolutas para poder acceder correctamente
    File file = new File(fullPath);
    List<String> content = new ArrayList<>();
    
    if (file.exists()) {
        content = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

        // Verificación de protección
        for (String line : content) {
            if (line.contains("#PROTECTED") || line.contains("#KEY=")) {
                System.out.println("[SYSTEM]> This file is protected and cannot be edited directly.");
                return;
            }
        }
    }

    if (file.exists()) {
        content = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        System.out.println("[EDITOR]> Current content:");
        for (int i = 0; i < content.size(); i++) {
            System.out.printf("%02d │ %s%n", i + 1, content.get(i));
        }
    } else {
        System.out.println("[EDITOR]> File does not exist. It will be created.");
    }

    System.out.println("\n[EDITOR]> Enter lines.");
    System.out.println("[EDITOR]> Type 'ed -help' to see available commands.");

    Scanner sc = new Scanner(System.in);
    List<String> newContent = new ArrayList<>(content);

    while (true) {
        String input = sc.nextLine();
        if(input.equalsIgnoreCase("ed -help")){
            System.out.println("Available commands:");
            System.out.println(":exit --------------------------------------- Exits without saving");
            System.out.println(":save ------------------------------------- Save changes and exit");
            System.out.println(":clear ------------------------------------- Removes all content");
            System.out.println(":write line [number] [text] ------------- Overwrites a line in a specific line-number");
            System.out.println(":newline [number] ---------------------- Makes a determined number of new lines (prevents out of bounds)");
            System.out.println(":undo ------------------------------------- Reverts the last change you made");
        }

        if (input.equalsIgnoreCase(":save")) {
            file.getParentFile().mkdirs();
            Files.write(file.toPath(), newContent, StandardCharsets.UTF_8);
            System.out.println("[EDITOR]> File saved to: " + fullPath);
            break;

        } else if (input.equalsIgnoreCase(":exit")) {
            System.out.println("[EDITOR]> Exiting without saving.");
            break;

        } else if (input.equalsIgnoreCase(":clear")) {
            newContent.clear();
            System.out.println("[EDITOR]> Cleared content.");

        } else if (input.toLowerCase().startsWith(":write line")) {
            try {
                String[] parts = input.split("\\s+", 4);
                if (parts.length < 4) {
                    System.err.println("[EDITOR]> Invalid syntax. Use: :write line [number] [text]");
                    continue;
                }

                int lineNumber = Integer.parseInt(parts[2]);
                String newLine = parts[3];

                if (lineNumber <= 0 || lineNumber > newContent.size()) {
                    System.err.println("[EDITOR]> Line " + lineNumber + " is out of bounds.");
                } else {
                    newContent.set(lineNumber - 1, newLine);
                    System.out.println("[EDITOR]> Line " + lineNumber + " updated.");
                }
            } catch (Exception e) {
                System.err.println("[EDITOR]> Error parsing line number.");
            }

        } else {
            newContent.add(input);
        }
    }
}

//-------------------------------------------------------------------------------------------------------------------------------------------//
public static void editBinaryFile(String fullPath) throws IOException, ClassNotFoundException {//Hacer más tarde
    BinaryFile file = new BinaryFile(fullPath);
    Object obj = file.read();

    if (obj == null) {
        System.err.println("[EDITOR]> ERROR: File is empty or corrupted.");
        return;
    }

    Scanner sc = new Scanner(System.in);

    if (obj instanceof UserData) {
        UserData data = (UserData) obj;

        System.out.println("[EDITOR]> Current UserData:");
        System.out.println("1. Username: " + data.getUsername());
        System.out.println("2. Role: " + data.getRole());

        System.out.print("New username (leave blank to keep): ");
        String username = sc.nextLine();
        if (!username.trim().isEmpty()) {
            data.setUsername(username);
        }

        System.out.print("New role (leave blank to keep): ");
        String role = sc.nextLine();
        if (!role.trim().isEmpty()) {
            data.setRole(role);
        }

        file.save(data);
        System.out.println("[EDITOR]> UserData updated and saved.");
    } else {
        System.err.println("[EDITOR]> ERROR: Unsupported object type.");
    }
}

}//class