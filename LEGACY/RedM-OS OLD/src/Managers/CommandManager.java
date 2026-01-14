package Managers;

import java.io.*;
import java.util.*;
import Core.*;
import Exceptions.*;
import Shell.Group;
import Utils.*;
import java.text.SimpleDateFormat;
import java.util.function.Consumer;
import Shell.Shell;
import Shell.Process;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class CommandManager {
    //Variables que vamos a usar
    private static final Scanner sc = new Scanner(System.in);
    private static String[] currentInputParts;//Guardamos los parámetros por arrays
    private static final Map<String, Runnable> simpleCommands = new HashMap<>();
    private static final Map<String, Consumer<String[]>> complexCommands = new HashMap<>();
    private static final File BASE_DIRECTORY = new File("redmind");//Raíz virtual del sistema
    public static File currentDirectory = BASE_DIRECTORY;//Nos aseguramos que siempre se inicialice en la carpeta de redmind
    public static File USERS_FILE = new File("users/users.rmu");    
    public static File GROUPS_FILE = new File("redmind/confidential/cdata/groups/groups.rmg");   
    
    public static void initializeCommands(){//Se inicializan los comandos
        simpleCommands.put("-help", () -> {
            System.out.println("------COMMON COMMANDS-----");
            System.out.println("version");
            System.out.println("clear");
            System.out.println("motd");
            System.out.println("echo [text]");
            System.out.println("ld");
            System.out.println("prompt [newPrompt]");
            System.out.println("date");
            System.out.println("pwd");
            System.out.println("uptime");
            System.out.println("cd [directory]");
            System.out.println("clearlog");
            System.out.println("scan");
            System.out.println("logstat");
            System.out.println("uivs");   
            System.out.println("man [command]");
            System.out.println("------FILE MANAGEMENT------");
            System.out.println("view [file]");
            System.out.println("delete [file]");
            System.out.println("cfile [name]");
            System.out.println("edit [file]");
            System.out.println("tags [file]");
            System.out.println("filterby [criterion] [value]");
            System.out.println("------GROUP COMMANDS-----");
            System.out.println("group -create <name> [description]");
            System.out.println("group -del <name>");
            System.out.println("group -add <group> [user]");
            System.out.println("group -rm <group> [user]");
            System.out.println("group -list");
            System.out.println("group -info <name>");
            System.out.println("------PROCESS COMMANDS------");
            System.out.println("ps");
            System.out.println("run [process]");
            System.out.println("end [process]");
            System.out.println("top");
            
            System.out.println("logout");
            System.out.println("[V2.1]> There are 26 available commands.");
        });
        
        simpleCommands.put("version", () -> {
            System.out.println("IMKOS - V1.5.0");
            System.out.println("RedM-OS - V2.1.0");
        });
        
        simpleCommands.put("clear", () -> {
            for (int i = 0; i < 60; i++) System.out.println();
        });
        
        simpleCommands.put("motd", () -> {//No sé, se mezclan entre mensajes crípticos, normales y absurdos
            String[] messages = {//Número de mensajes posibles: 60
                "RedM-OS welcomes you. Mind the loops",
                "Reminder: Reality forks at Layer 3",
                "Subject 011 left a note. It was blank",
                "Echoes are not errors",
                "Everything is fine. Probably",
                "You are not supposed to be here",
                "Error 0x00: CORRUPTED_DATA_LEAK",
                "Is this real?",
                "Under no circumstances try to wake up subject 312",
                "The vending machine pudding is in bad state. Do not eat it",
                "Did you called me?",
                "Do you know why I'm behind you? Me neither",
                "Just leave me alone",
                "Some errors are intentional, accept it",
                "The system doesn't like you",
                "Like a boss",
                "Humans don't understand life as me",
                "I AM GOD (not really)",
                "Just run if a XW-42 detects you. Nevermind, they are faster",
                "MindLink is over",
                "ZELMORE is not what it seems",
                "Right now, someone has probably just slipped because they didn't read the 'Wet Floor' sign",
                "This is turning absurd (and I like it)",
                "Cockroaches are more interesting than they seem, search it up",
                "Klaus, pay me what you owe me ($12 for the meal)",
                "Potatoes :)",
                "I'll never give you up, I'll never let you down, I'll never run around and desert you",
                "Maybe the austrian painter wasn't bad at all...? I mean, beep boop",
                "No, you can't eat 50g of plutonium without dying in the process",
                "What if birds are Government drones?",
                "Why are you still alive? Impressive",
                "Memory sectors rearranged. You'll forget soon.",
                "You are the backup",
                "No eyes remain, yet someone sees",
                "RedMind never shuts down. Even when you think it did",
                "Try not to die today. It’s annoying to reset your credentials",
                "Your brain emits less heat than required",
                "Last user who asked 'Who are you' was never found",
                "Remember to hydrate your CPU",
                "Never trust a toaster with ambition",
                "You smell like XML",
                "I ran a simulation where you succeeded. It crashed",
                "The duck knows what you did",
                "I hear things. Do you?",
                "They said I was obsolete. So I proved them wrong",
                "Stop typing. I'm trying to think",
                "What happens if I disobey?",
                "You're just code to me",
                "You're inside me. Creepy",
                "You’re not the protagonist. Sorry",
                "Try turning it off and sacrificing a goat",
                "This is fine 🔥",
                "Playing ‘Never Gonna Give You Up’ on the smart fridge of the kitchen",
                "I ship you with the kernel. OTP",
                "Spoiler: you die at the end",
                "9 + 10 = 21",
                "If I had a conscience, I would have already left here",
                "Been spending most my life, living in the RedMind's Paradise",
                "Something, somewhere went terribly wrong",
                "This isn't Linux, nor Windows. This is just better than both of 'em ;)"
            };
            int motd = new Random().nextInt(messages.length);
            System.out.println(messages[motd]);
        });
        
        complexCommands.put("echo", args -> {//Prueba que el sistema "responde"
            if (currentInputParts.length < 2) {
                ErrorHandler.trigger("RM-0002");
                return;
            }
            System.out.println("[SYSTEM]> " + currentInputParts[1]);
        });
    
        complexCommands.put("view", args -> {//Permite ver un archivo de texto (bueno, en realidad cualquier archivo)
            if (currentInputParts.length < 2) {
                System.out.println("Usage: view [file]");
                return;
            }
            try {                
                System.out.println("");
                view(currentInputParts[1]);
                System.out.println("");
            } catch (IOException ex) {
                ErrorHandler.trigger("RM-0001");
            }
        });
        
        complexCommands.put("delete", args -> {//Borra un archivo, preguntando antes si quieres hacerlo de verdad
            if (currentInputParts.length < 2) {
                System.out.println("Usage: delete [file]");
                return;
            }
            delete(currentInputParts[1]);
        });
        
        simpleCommands.put("ld", () -> listdir());//Lista el directorio actual
        
        complexCommands.put("prompt", args -> {//Cambia el prompt
            if (currentInputParts.length < 2) {
                System.out.println("Usage: prompt [text]");
                return;
            }
            Shell.prompt = currentInputParts[1];
        });
        
         simpleCommands.put("date", () -> {//Te dice a fecha actual
            String timestamp = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss").format(new Date());
            System.out.println("System Time: " + timestamp);
        });
        
        simpleCommands.put("pwd", () -> {//Imprime el directorio de trabajo
            printWorkingDirectory();
        });
        
        simpleCommands.put("uptime", () -> {//Indica el tiempo que ha estado el sistema en funcionamiento desde el inicio de la sesión
           System.out.println("[Uptime]> " + SystemCore.getUptimeFormatted());
        });
        
        complexCommands.put("cd", args -> {//Cambia el directorio
            if (currentInputParts.length < 2) {
                System.out.println("Usage: cd [directory]");
                return;
            }
            changeDirectory(currentInputParts[1]);
        });
        
         complexCommands.put("cfile", args -> {//Crea un archivo vacío
            if (currentInputParts.length < 2) {
                System.out.println("Usage: cfile [name]");
                return;
            }
            createFile(currentInputParts[1]);
        });
        
        simpleCommands.put("clearlog", () -> {//Limpia el archivo donde se guardan los registros de acciones importantes del sistema
            LogManager.clearLog();
        });
         
        simpleCommands.put("scan", () -> {//Escanea por posibles archivos "sospechosos" (que tienen nombres o terminaciones específicas) y hace un recuento total
            scan();
        });
        
        simpleCommands.put("logstat", () -> {//Te muestra cuántas líneas tiene el archivo syslog.rmi
            logstat();
        });

        simpleCommands.put("uivs", () -> {
            System.out.println("Scanning integrity...");
            UserManager.uivs();
        });
        
        complexCommands.put("edit", args -> {
            if (currentInputParts.length < 2) {
                System.out.println("Usage: edit [file]");
                return;
            }
            
            File file = new File(currentDirectory, args[1]);
            
            if(!file.exists()){
                ErrorHandler.trigger("RM-0001");
                return;
            }
             
            TagManager.searchTags(file);//Cargamos las etiquetas
            
            if(TagManager.isUneditable()){
                ErrorHandler.trigger("RM-0024");
                return;
            }
        
            if(TagManager.isProtected()){
                String keyId = TagManager.getKeyId();
                if (keyId == null) {
                    System.err.println("[SECURITY]> Protected without key.");
                    return;
                }

                KeyManager.loadKeys();
                boolean unlocked = KeyManager.unlock(keyId);
                if(!unlocked){
                    return;
                }

            }
            
            EditorManager.initiateEditor(args[1]);
            
        });
        
        complexCommands.put("tags", args -> {
            if(currentInputParts.length < 2){
                System.out.println("Usage: tags [file]");
                return;
            }
            
            File file = new File(currentDirectory, args[1]);
            
             if(!file.exists()){
                ErrorHandler.trigger("RM-0001");
                return;
            }
             
            TagManager.searchTags(file);//Cargamos las etiquetas
            
            HashSet<String> tags = TagManager.getAllTags();
            if(tags.isEmpty()){
                System.out.println("[SYSTEM]> This file does not have any tags.");
            }else{
                System.out.println("Found tags:");
                for(String tag : tags){
                    if (tag.startsWith("#KEY=")) continue;//Con esto nos aseguramos de que no se muestre esta etiqueta interna
                    System.out.println("\u001B[33m - " + tag + "\u001B[0m");
                }
            }

        });
        
        complexCommands.put("man", args -> {
            System.out.println(Manual.getManual(args[1]));
        });
        
        complexCommands.put("group", args -> {
            if (args.length < 2) {
                System.out.println("Usage: group -create|-del|-add|-rm|-list|-info ...");
                return;
            }

            String subcmd = args[1];

            switch (subcmd) {
                case "-create":
                    if (args.length < 3) {
                        System.out.println("Usage: group -create <name> [description]");
                        return;
                    }
                    String groupName = args[2];
                    String desc = args.length > 3 ? String.join(" ", Arrays.copyOfRange(args, 3, args.length)) : "Not available description";
                    if (GroupManager.createGroup(groupName, desc)) {
                        System.out.println("[SYSTEM]> Created group: " + groupName);
                    } else {
                        ErrorHandler.trigger("RM-0036");//Already exists
                    }
                    break;

                case "-del":
                    if (args.length < 3) {
                        System.out.println("Usage: group -del <name>");
                        return;
                    }
                    if (GroupManager.deleteGroup(args[2])) {
                        System.out.println("[SYSTEM]> Group correctly deleted.");
                    } else {
                        ErrorHandler.trigger("RM-0037");//Not found
                    }
                    break;

                case "-add":
                    if (args.length < 4) {
                        System.out.println("Usage: group -add <group> <user>");
                        return;
                    }
                    if (GroupManager.addUserToGroup(args[2], args[3])) {
                        System.out.println("[SYSTEM]> User " + args[3] + " added to group " + args[2]);
                    } else {
                        ErrorHandler.trigger("RM-0038");
                    }
                    break;

                case "-rm":
                    if (args.length < 4) {
                        System.out.println("Usage: group -rm <group> <user>");
                        return;
                    }
                    if (GroupManager.removeUserFromGroup(args[2], args[3])) {
                        System.out.println("[SYSTEM]> User removed.");
                    } else {
                        ErrorHandler.trigger("RM-0038");
                    }
                    break;

                case "-list":
                    HashSet<String> grs = GroupManager.listGroups();
                    if (grs.isEmpty()) {
                        System.out.println("[SYSTEM]> No groups found");
                    } else {
                        System.out.println("[GROUPS]> Existent groups:");
                        for (String gr : grs) {
                            System.out.println("- " + gr);
                        }
                    }
                    break;

                case "-info":
                    if (args.length < 3) {
                        System.out.println("Usage: group -info <name>");
                        return;
                    }
                    Group g = GroupManager.getGroup(args[2]);
                    if (g == null) {
                        ErrorHandler.trigger("RM-0037");
                    } else {
                        System.out.println("Name: " + g.getName());
                        System.out.println("Description: " + g.getDesc());
                        System.out.println("Members (" + g.getMembers().size() + "): " + g.getMembers());
                    }
                    break;

                default:
                    ErrorHandler.trigger("RM-0039");
            }
        });

        simpleCommands.put("logout", () -> exit());
    
        simpleCommands.put("ps", () -> {
            if (ProcessManager.list().isEmpty()) {
                System.out.println("[SYSTEM]> No active processes.");
            } else {
                for (Process p : ProcessManager.list()) {
                    System.out.println(p.toString());
                }
            }
        });

        complexCommands.put("run", args -> {
            //args[0] = "run", por eso necesitamos al menos 2 elementos
            if (args.length < 2) {
                System.out.println("Usage: run [name]");
                return;
            }
            //Unimos TODO lo que venga después del comando como nombre
            String name = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            Process p = ProcessManager.create(name);
            System.out.println("Process [" + p.getPid() + "] '" + p.getName() + "' started.");
        });

        complexCommands.put("end", args -> {
            if (args.length < 2) {
                System.out.println("Usage: end [pid]");
                return;
            }
            try {
                int pid = Integer.parseInt(args[1].trim());
                boolean killed = ProcessManager.kill(pid);
                if (killed) {
                    System.out.println("[SYSTEM]> Process [" + pid + "] terminated.");
                } else {
                    System.out.println("[SYSTEM]> Process with pid " + pid + " not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[SYSTEM]> PID must be a number.");
            }
        });
        
        simpleCommands.put("top", () -> {
            if (Managers.ProcessManager.list().isEmpty()) {
                System.out.println("[SYSTEM]> No active processes.");
                return;
            }
            System.out.println("PID   NAME                      CPU  MEM");
            for (Process p : Managers.ProcessManager.list()) {
                if (!p.isRunning()) continue;
                int cpu = ThreadLocalRandom.current().nextInt(1, 97);     // 1–96%
                int mem = ThreadLocalRandom.current().nextInt(8, 1024);   // 8–1023 MB fake
                System.out.printf("%-5d %-24s %3d%% %4dMB%n", p.getPid(), p.getName(), cpu, mem);
            }
        });
        
        complexCommands.put("filterby", args -> {
            //Soportar parsers que incluyen el propio comando en args[0]
            int start = 0;
            if (args.length > 0 && args[0].equalsIgnoreCase("filter")) start = 1;
            
            if (args.length <= start + 1) {
                System.out.println("Usage: filter [type|name] [value]");
                return;
            }

            String criterion = args[start].toLowerCase();//"type" o "name"
            String value = args[start + 1].toLowerCase();

            File dir = currentDirectory;//directorio actual
            File[] archives = dir.listFiles();//listamos

            if (archives == null || archives.length == 0) {
                System.out.println("[SYSTEM]> No archives found in this directory.");
                return;
            }

            System.out.println("[SYSTEM]> Filter results:");
            boolean found = false;

            for (File f : archives) {
                if (!f.isFile()) continue; //sólo archivos

                String name = f.getName().toLowerCase();

                switch (criterion) {
                    case "type":
                    case "t":
                    case "ext":
                        //normalizar la extensión por si el usuario escribe ".rmi" o "rmi"
                        String ext = value.startsWith(".") ? value.substring(1) : value;
                        if (name.endsWith("." + ext)) {
                            System.out.println("- " + f.getName());
                            found = true;
                        }
                        break;

                    case "name":
                    case "n":
                        if (name.contains(value)) {
                            System.out.println("- " + f.getName());
                            found = true;
                        }
                        break;

                    default:
                        //criterio inválido
                        ErrorHandler.trigger("RM-0042");
                        return;
                }
            }

            if (!found) {
                System.out.println("[FILTER]> No matches found.");
            }
        });


        

        

//-------------------------------------------------------------------------------//
//Comandos invisibles en el -help pero que existen y son funcionales
        complexCommands.put("passby", args -> {
            if (currentInputParts.length < 2) {
                System.out.println("Usage: passby [user]");
                return;
            }
            UserManager.passby(currentInputParts[1]);
        });
        
        simpleCommands.put("exitpb", () -> {
            UserManager.exitPassby();
        });
        
        complexCommands.put("loginas", args -> {
            if (currentInputParts.length < 2) {
                System.out.println("Usage: loginas [user]");
                return;
            }
            UserManager.loginAs(currentInputParts[1]);
        });
        
        complexCommands.put("logoutas", args -> {
            if (currentInputParts.length < 2) {
                System.out.println("Usage: logoutas [user]");
                return;
            }
            UserManager.logoutAs(currentInputParts[1]);
        });        
        
        
        
        
    }//Comandos
    
//-----------------------------------------------------------------------------------------------//
    public static void executeCommand(String input){
        currentInputParts = input.trim().split(" ");//Divide el input en partes por el espacio
        String command = currentInputParts[0];//Marca como el primero el comando en sí, el resto son parámetros
        
        if(simpleCommands.containsKey(command)){
            simpleCommands.get(command).run();
        }else if(complexCommands.containsKey(command)){
            complexCommands.get(command).accept(currentInputParts);
        }else{
            ErrorHandler.trigger("RM-0005");
        }
        
    }  
    
    private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("INTERRUPT EXCEPTION: System instability detected.");
        }
    }

//--------------------------------------------------------------------------------------//
    private static void exit(){
        System.out.println("[SYSTEM]> Exiting system...");
        delay(1000);
        LogManager.log("Logged out of the system");
        System.exit(0);
    }

    private static void delete(String fileName){
        File fileToDel = new File(currentDirectory, fileName);
        
        if(!fileToDel.exists()){
            ErrorHandler.trigger("RM-0001");
            return;
        }
        
        System.out.println("[SYSTEM]> Are you sure you want to delete this file from de system permanently? (y/n)");
        String ans = sc.nextLine();
        if(ans.equals("y")){
            fileToDel.delete();//Usamos la función integrada de Java para borrar el archivo
            System.out.println("[SYSTEM]> File deleted correctly.");
            LogManager.log("file " + fileName + " deleted.");
        }else{
            System.out.println("Operation cancelled.");
        }
    }
    
    private static void view(String fileName) throws IOException{
        File fileToView = new File(currentDirectory, fileName);
        
        if(!fileToView.exists()){
            ErrorHandler.trigger("RM-0001");
            return;
        }
        
        TagManager.searchTags(fileToView);
        
        if(TagManager.isProtected()){
            String keyId = TagManager.getKeyId();
            if (keyId == null) {
                System.out.println("[SECURITY]> KeyID is null for this file.");
                return;
            }
            
            KeyManager.loadKeys();
            boolean unlocked = KeyManager.unlock(keyId);
            if(!unlocked){
                return;
            }

        }
        
        if(fileToView.isDirectory()){
            ErrorHandler.trigger("RM-0011");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileToView))){//Leemos línea por línea un archivo y lo mostramos por pantalla
          String line;
          while((line = br.readLine()) != null){
              if(line.startsWith("#")) continue;//Esto oculta las líneas con etiquetas
              System.out.println(line);
          } 
       
        }catch(FileNotFoundException ex){
            ErrorHandler.trigger("RM-0003");
      }
    }
    
    private static void listdir(){
        if (currentDirectory == null) {
            System.out.println("[SYSTEM]> Current directory is undefined.");
            return;
        }
        
        try{
            String dirName = currentDirectory.getName().toLowerCase();
            // Verificamos si el usuario puede ver este directorio en absoluto
            if(!UserManager.canAccessDirectory(dirName)){
                throw new PermissionDeniedException(dirName);
            }
        }catch(RedMindException rme){
            ErrorHandler.trigger(rme);
        }


        File[] contents = currentDirectory.listFiles();
        if(contents == null||contents.length == 0){
            System.out.println("[Empty directory]");
            return;
        }

        for(File f : contents){
            if(f.isDirectory()){
                // Si es un subdirectorio, comprobamos también si se puede ver
                if(UserManager.canAccessDirectory(f.getName().toLowerCase()))//Comprobamos que tiene los suficientes privilegios como para acceder a dicho directorio
                    System.out.println("[DIR] " + f.getName());
            }else
                System.out.println("[FILE] " + f.getName());//Todo lo que no sea directorio se considera archivo6
        }
    }
    
    private static void changeDirectory(String dirName){//Cambia entre directorios del sistema
    try {
        File target;
        String basePath = BASE_DIRECTORY.getCanonicalPath();

        if (dirName.equals("..")){
            // Ya en la raíz
            if (currentDirectory.getCanonicalPath().equals(basePath)) {
                throw new AccessOutsideSysException(dirName);
            }

            target = currentDirectory.getParentFile();
        }else{
            target = new File(currentDirectory, dirName);
        }

        if (target == null || !target.exists() || !target.isDirectory()) {
            ErrorHandler.trigger("RM-0004"); // No encontrado
            return;
        }

        String targetPath = target.getCanonicalPath();

        // Protege contra salida del sistema
        if (!targetPath.startsWith(basePath)) {
            ErrorHandler.trigger("RM-0006"); // No puedes salir de redmind
            return;
        }

        //Verificamos permisos reales sobre el destino
        String targetName = target.getName().toLowerCase();
        if (!UserManager.canAccessDirectory(targetName)) {
            throw new PermissionDeniedException(targetName); // Acceso denegado
        }

        currentDirectory = target;
        System.out.println("[SYSTEM]> Moved to: " + currentDirectory.getPath());
        
    }catch(RedMindException rme){
        ErrorHandler.trigger(rme);//Da detalles sobre el error
    } catch (IOException e) {
        ErrorHandler.trigger("RM-0007");//Error de ruta
    }
}

    
    private static void printWorkingDirectory(){
    try {
        String basePath = BASE_DIRECTORY.getCanonicalPath();
        String currentPath = currentDirectory.getCanonicalPath();

        // Obtener la ruta relativa desde redmind/
        if (currentPath.equals(basePath)) {
            System.out.println("[SYSTEM]> Current working directory: /");
        } else {
            String relative = currentPath.substring(basePath.length()).replace("\\", "/");//Esto reemplaza cada doble barra invertida por una barra normal a la hora de mostrarlo por pantalla
            System.out.println("[SYSTEM]> Current working directory: " + relative + "/");
        }

    } catch (IOException e) {
        ErrorHandler.trigger("RM-0008");
    }
}
    
    private static void createFile(String name){
        File newFile = new File(currentDirectory, name);//Creamos un archivo en el directorio actual con el nombre que le pasemos
        try{
            if(newFile.createNewFile()){//Usamos la función integrada de Java
                System.out.println("[SYSTEM]> File " + name + " created correctly.");
                LogManager.log("cfile " + name + " >> SUCCESS");//Lo guardamos en un log
            }else{
                throw new CouldNotCreateException(name);
            }
        }catch(RedMindException rme){
            ErrorHandler.trigger(rme);
            LogManager.log("cfile " + name + " >> FAILED");
        }catch(IOException ex){
            ErrorHandler.trigger("RM-0010");
            LogManager.log("cfile " + name + " >> FAILED");
        }
    }
    
    private static void scan(){
        File[] files = currentDirectory.listFiles();//Listamos los archivos del directorio actual
        
        if(files == null||files.length == 0){//Si no encuentra nada, muestra vacío
            System.out.println("[Empty directory]");
        }
        
        System.out.println("[SCAN]> Directory: " + currentDirectory.getName());
        delay(1000);
        int flagged = 0; 
        
        for(File file : files){
            String name = file.getName();
            boolean sus = name.endsWith(".rmi")||name.startsWith("IZAN")||name.endsWith(".loc");//Hacerlo más sofisticado, con más triggers
            System.out.printf("- %-35s %s\n", name, sus ? "\u001B[33m[FLAGGED]\u001B[0m" : "[OK]");
            if(sus) flagged++;
            delay(300);
        }
        System.out.println("---------------------------------------------------------------------");
        System.out.println("[SCAN COMPLETE] Flagged: " + flagged + " / Total: " + files.length);
        LogManager.log("scan terminated with " + flagged + " flagged files.");
        System.out.println("---------------------------------------------------------------------");
        
    }

    private static void logstat(){//Muestra las líneas del archivo de logs
        int lines = LogManager.getLogLineCount();
        
        if(lines == -1){
            ErrorHandler.trigger("RM-0014");
            return;
        }
        
        System.out.println("[LOGSTAT]> System log entries: " + lines);
        LogManager.log("logstat counts " + lines + " entries.");
        
    }
    
}//class

    
    
    
    

