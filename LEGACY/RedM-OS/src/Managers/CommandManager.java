package Managers;

import FS.VDManager;
import Sys.*;
import java.util.*;
import Parser.*;
import Sys.BootModule.*;
import Security.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class CommandManager {
    private static String[] currentInputParts;//Guardamos los parámetros por arrays
    private static final Map<String, Runnable> simpleCommands = new HashMap<>();
    private static final Map<String, Consumer<String[]>> complexCommands = new HashMap<>(); 
    private static VDManager vdm = new VDManager(FS.FS.getBaseDir());
    
    
    //Método principal que ejecuta los comandos
    public static void execute(String inputLine) {
        if (inputLine == null || inputLine.trim().isEmpty()) return;

        currentInputParts = inputLine.trim().split(" ");
        String command = currentInputParts[0];

        //Primero comprobamos si es simple
        if (simpleCommands.containsKey(command)) {
            simpleCommands.get(command).run();
            return;
        }
        
        //Luego si es complejo
        if (complexCommands.containsKey(command)) {
            complexCommands.get(command).accept(currentInputParts);
            return;
        }
        
        ErrorHandler.trigger("001", command);

    }
    
    //Inicializamos los comandos en memoria
    public static void initialize(){
        simpleCommands.put("-help", () -> {
            System.out.println("----COMMON COMMANDS----");
            System.out.println("ver");
            System.out.println("pwd");
            System.out.println("echo [text]");
            System.out.println("ld");
            System.out.println("cd [directory]");
            System.out.println("cdir [name]");
            System.out.println("clear");
            System.out.println("man [command]");
            System.out.println("----FILE SYSTEM-----");
            System.out.println("cfile [name] [ext]");
            System.out.println("write [name]");
            System.out.println("del [file]");
            System.out.println("move [file] [targetDirectory]");
            System.out.println("view [file]");
            System.out.println("----INTERNAL MANAGEMENT----");
            System.out.println("lock");
            System.out.println("reboot");
            System.out.println("reconstruct");
            System.out.println("exit");
        });
//-----------------------------------------------------------------------------------------------------//
//COMMON COMMANDS
        simpleCommands.put("ver", () -> {
            System.out.println("IMKOS - V1.2.0");
            System.out.println("RedM-OS - " + Sys.getVersion());
        });
        
        simpleCommands.put("pwd", () -> {
            vdm.pwd();
        });
        
        complexCommands.put("echo", args -> {
            if (args.length < 2) {
                ErrorHandler.trigger("002", "Usage: echo [textToReturn]");
                return;
            }
            System.out.println("[SYSTEM]> " + String.join(" ", args).substring(5));
        });
        
        simpleCommands.put("ld", () -> {
            vdm.ld();
        });
        
        complexCommands.put("cd", args -> {
            if (args.length < 2) {
                ErrorHandler.trigger("002", "Usage: cd [targetDirectory]");
                return;
            }
            vdm.cd(args[1]);
        });
        
        complexCommands.put("cdir", args -> {
            if (args.length < 2) {
                ErrorHandler.trigger("002", "Usage: cdir [nameForDirectory]");
                return;
            }
            vdm.mkdir(args[1]);
        });
        
        simpleCommands.put("clear", ()-> {
            Sys.clear();
        });
        
        complexCommands.put("man", args -> {
            if (args.length < 2) {
                ErrorHandler.trigger("002", "Usage: man [command]");
                return;
            }
            accessManual(args[1]);
        });
//---------------------------------------------------------------------------------------------------------//
//FILE SYSTEM
        
        complexCommands.put("cfile", args -> {
            if (args.length < 2) {
                ErrorHandler.trigger("002", "Usage: write [file.extension]");
                return;
            }
            FS.FS.createFile(args[1], args[2]);
        });
        
        complexCommands.put("write", args -> {
            if (args.length < 2) {
                ErrorHandler.trigger("002", "Usage: write [file.extension]");
                return;
            }
            FS.FS.createFileInteractive(args);
        });
        
        complexCommands.put("del", args -> {
            if (args.length < 2) {
                ErrorHandler.trigger("002", "Usage: del [fileToDelete]");
                return;
            }
            FS.FS.deleteFile(args[1]);
        });
        
        complexCommands.put("move", args -> {
            if (args.length < 2) {
                ErrorHandler.trigger("002", "Usage: move [fileToMove] [destination]");
                return;
            }
            FS.FS.moveFile(args[1], args[2]);
        });
        
        complexCommands.put("view", args -> {
            if (args.length < 2) {
                ErrorHandler.trigger("002", "Usage: view [fileTarget]");
                return;
            }
            FS.FS.viewFile(args[1]);
        });
//---------------------------------------------------------------------------------------------------------//
//INTERNAL MANAGEMENT
        simpleCommands.put("lock", () -> {
            
        });
        
        











        simpleCommands.put("exit", () -> {
            Log.write("System shutdown", "-");
            Log.save("redmind/logs/logs.txt");
            Sys.shutdown();
        });
        
    }
    
    //SECCIÓN DE DELAYSS
    
    public static void delaySec(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // Si el hilo es interrumpido, restauramos el estado de interrupción
            Thread.currentThread().interrupt();
        }
    }
    
    //COMANDOS QUE HAY QUE METER EN PAQUETES
public static void accessManual(String command){
    switch(command){
	case "ver":
            System.out.println("Usage: ver");
            System.out.println("Show kernel and system version");
            break;
        case "pwd":
            System.out.println("Usage: pwd");
            System.out.println("Prints the current working directory");
            break;
        case "echo":
            System.out.println("Usage: echo [text]");
            System.out.println("The system talks back to the user");
            break;
	case "ld":
            System.out.println("Usage: ld");
            System.out.println("List all the files on the current directory. In case there wasn't any file, it shows 'empty directory'");
            break;
        case "cd":
            System.out.println("Usage: cd [targetDirectory]");
            System.out.println("Changes directory");
            break;
        case "cdir":
            System.out.println("Usage: cdir [directoryName]");
            System.out.println("Creates an empty directory named as the parameter");
            break;
        case "cfile":
            System.out.println("Usage: cfile [fileName]");
            System.out.println("Creates an empty file");
            break;
        case "del":
            System.out.println("Usage: del [file]");
            System.out.println("Deletes a file");
            break;
        case "exit":
            System.out.println("Usage: exit");
            System.out.println("Terminates the program");
            break;
        case "write":
            System.out.println("Usage: write [fileName]");
            System.out.println("Lets you create and edit a file");
            break;
        case "move":
            System.out.println("Usage: move [file] [targetDirectory]");
            System.out.println("Moves a file from the actual directory to another one");
            break;
        case "view":
            System.out.println("Usage: view [file]");
            System.out.println("It lets you see the content of a file");
            break;
        case "clear":
            System.out.println("Usage: clear");
            System.out.println("Clears the screen");
            break;
        case "man":
            System.out.println("Usage: man [command]");
            System.out.println("Shows the usage of the commands");
            break;
        default:
            System.out.println("Usage: man [command]");
        }
    }
    
    
    
    
    
    
    
    
    
    
}
