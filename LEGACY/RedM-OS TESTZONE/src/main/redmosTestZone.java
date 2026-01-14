package main;

import IO.*;
import Sys.*;
import FS.*;
import Security.*;



public class redmosTestZone {   
    static String username = "";
    static String password = "";
    public static void main(String[] args) { 
        //Registramos módulos para información y la versión actual
        //También colocamos el directorio base para que el usuario no se pueda salir de ahí
        VDManager vdm = new VDManager(FS.getBaseDir());
        System.out.println("");
        CommandRegistry registry = new CommandRegistry();
        Security.initSystemKey();
        System.out.println("");
        final String US_FILE = "redmind/control/users.txt";
        boolean booted = false;
        Sys.initiateKernel();
        Sys.setVersion("BETA-2");
        System.out.println("");
        boolean inSys = true;
        
        while(inSys){
            IO.output("[SYS]> If you want to change any system-level configuration, insert command 'config [optId] [newValue]'");
            IO.output("[SYS]> If you want to see what options are available before entering the boot module, insert command '-showconf'");
            IO.output("[SYS]> If you do not want to make any changes, press enter.");
            String input = IO.input("");

            switch(input){
                case "":
                    inSys = false;
                    break;
                case "-showconf":
                    System.out.println("");
                    IO.output("Actual configurations:");
                    System.out.println("-----------------------------------------");
                    ConfigManager.showConfig();
                    System.out.println("-----------------------------------------");
                    break;
                default:
                    if(input.startsWith("config ")){
                        String[] parts = input.substring(7).split(" ");
                        ConfigManager.applyCommand(parts);
                    }else{
                        System.err.println("Unrecognized command.");
                    }
            }
        }
        
        
        System.out.println("");
        IO.output("Loading configurations...");
        ConfigManager.setFolder("config");
        ConfigManager.init();
        System.out.println("");
        
        while(booted != true){
            IO.output("To start the program, please insert a valid boot code.");
            String boot = IO.input("> ");
            switch(boot){
                case "boot redmos -normalmode":
                    BootModule.bootNormal();
                    booted = true;
                    break;
                case "boot redmos -diagmode":
                    BootModule.bootDiagnostic();
                    booted = true;
                    break;
                case "fastboot":
                    booted = true;
                    break;
                default:
                    IO.output("Invalid boot code.");
                    break;
            }
        }
        
        IO.output("To access the terminal, you must log in with a valid username and password.");
        int atts = 3;
        boolean logged = false;
	while(!logged && atts > 0){
            String nom = IO.input("[USERNAME]> ");
            String passw = IO.input("[PASSWORD]> ");
		
            if(Security.authenticate(US_FILE, nom, passw) == true){
		System.out.println("[ACCESS GRANTED]");
                username = nom;
                password = passw;
		logged = true;
            }else{
		atts--;
		if(atts > 0){
                    System.out.println("User not found. Please insert a valid username.");
                    System.out.println("Attempts remaining: " + atts);
                }
		System.out.println("[ACCESS DENIED]");
            }
	}
        
        //Algunos comandos básicos
        registry.register("-help", cmd -> {
            IO.output("COMMAND LIST - REDM-OS");
            IO.output("echo [text]");
            IO.output("ver");
            IO.output("sinfo");
            IO.output("kinfo");
            IO.output("pwd");
            IO.output("ld");
            IO.output("cd");
            IO.output("cfile [name] [extension]");
            IO.output("copy [file] [destiny]");
            IO.output("cdir [name]");
            IO.output("lmod");
            IO.output("reboot");
            IO.output("reconstruct");
            IO.output("lock");
            IO.output("delete [file]");
            IO.output("genkey [context]");
            IO.output("exit");
        });
        
        registry.register("echo", cmd -> {
            for (String arg : cmd.args) IO.output(arg + " ");
        });

        registry.register("exit", cmd -> {
            Sys.shutdown();
        });
        
        registry.register("lmod", cmd -> {
            Sys.listModules();
        });
        
        registry.register("ver", cmd -> {
            Sys.getVersion();
        });
        
        registry.register("sinfo", cmd -> {
            Sys.getSystemInfo();
        });
        
        registry.register("kinfo", cmd -> {
            Sys.getKernelInfo();
        });
        
        registry.register("cfile", cmd -> {
            // cmd.args[0] = nombre del archivo, cmd.args[1] = extensión (opcional)
            String name = cmd.args[0];
            String ext = cmd.args[1];
            if(FS.exists(name) && FS.exists(ext)){
                IO.output("This file is already created.");
            }else{
                FS.createFile(name, ext);
            }
        });
        
        registry.register("pwd", cmd -> {
            vdm.pwd();
        });
        
        registry.register("ld", cmd -> {
            vdm.ld();
        });
        
        registry.register("cdir", cmd -> {
            String name = cmd.args[0];
            vdm.mkdir(name);
        });
        
        registry.register("cd", cmd -> {
            String dir = cmd.args[0];
            vdm.cd(dir);
        });
        
        registry.register("reboot", cmd -> {
            BootModule.reboot();
        });
        
        registry.register("reconstruct", cmd -> {
           BootModule.reconstruct();
        });
        
        registry.register("lock", cmd -> {
           Security.lock(username, password);
        });
        
        registry.register("copy", cmd -> {
           String f = cmd.args[0];
           String folder = cmd.args[1];
           FS.copyFile(f, folder);
        });
        
        registry.register("delete", cmd -> {
            IO.output("To perform this action, you must put a valid key");
            String enteredKey = IO.input("> ");
            if(Security.validateTempKey(enteredKey)){
                FS.deleteFile(cmd.args[0]);
            }else{
                IO.output("[SEC]> Invalid or expired key. Deletion aborted.");
            }
        });
        
        registry.register("genkey", cmd -> {
            String context = cmd.args.length > 0 ? cmd.args[0] : "default";
            String key = Security.generateTempKey(context);
            IO.output("[SEC]> Temporary key generated for context: " + context);
            IO.output("[KEY]> >> " + key);
        });

        
        registry.register("showlogs", cmd -> {
           Log.show();
        });
        
        
        
            
        
        if(logged == true){
            IO.output("REDMIND TERMINAL | [PROTOTYPE]");
            while(true){
                String comm = IO.input("> ");
                Command cmd = Command.parseCommand(comm);
                registry.execute(cmd);
            }
        }else{
            System.exit(0);
        }
          
    } 
    
    
}

        
        
        
       

    

