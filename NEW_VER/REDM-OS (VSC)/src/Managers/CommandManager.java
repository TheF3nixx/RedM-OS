package Managers;

import java.util.HashMap;

import Shell.Commands.*;
import Shell.Commands.ComplexComms.*;

import System.SystemInfo;
import System.SystemMisc;
import System.SystemState;
import IO.IO;

public class CommandManager {

    private static final HashMap<String, SimpleCommand> simple = new HashMap<>();
    private static final HashMap<String, Command> complex = new HashMap<>();

    public static void register(String name, SimpleCommand cmd) {
        simple.put(name, cmd);
    }

    public static void register(Command cmd) {
        complex.put(cmd.getName(), cmd);
    }

    public static void execute(String input) {
        ParsedCommand p = CommandParser.parse(input);

        if (simple.containsKey(p.name())) {
            simple.get(p.name()).run();
            return;
        }

        Command c = complex.get(p.name());
        if (c == null) {
            ErrorHandler.trigger("024", input);
            return;
        }

        c.execute(p.args(), p.flags());
    }

    public static void init(){
        //COMANDOS SIN ARGUMENTOS
        register("uptime", () -> {
            IO.output("Uptime (seconds): " + SystemState.getUptime());
        });
        register("ld", () -> {
            FS.FSDirectories.ld();
        });
        register("pwd", () -> {
            FS.FSDirectories.pwd();
        });
        register("-help", () -> {
            help();
        });
        register("sysmode", () -> {
            IO.output("Current system mode: " + SystemState.getMode());
        });
        register("sysinfo", () -> {
            SystemInfo.getAllInfo();
        });
        register("logout", () -> {
            IO.pulseLoader("Shutting down...", 30, 120);
            IO.output("[SYSTEM]> Shutted down successfully.");
            System.exit(0);
        });
        register("motd", () -> {
            SystemMisc.motd();
        });
        register("clear", () -> {
            IO.clear();
        });
        register("idle", () -> {
            IO.pause("[SYSTEM]> Session paused.");;
        });
        register("date", () -> {
            SystemMisc.date();
        });
        
        //COMANDOS CON ARGUMENTOS
        register(new Cd());
        register(new Mkdir());
        register(new Rmdir());
        register(new CreateFile());
        register(new CreateEditableFile());
        register(new CopyFile());
        register(new DeleteFile());
        register(new DestroyFile());
        register(new RestoreFile());
        register(new MoveFile());
        register(new ViewFile());

        //COMANDOS INÚTILES PERO FÁSILES
        register(new Echo());

    }

    public static void help(){
        IO.output("-----AVAILABLE COMMANDS (22)-----");
        IO.output("sysinfo");
        IO.output("motd");
        IO.output("idle");
        IO.output("clear");
        IO.output("date");
        IO.output("uptime");
        IO.output("sysmode");
        IO.output("pwd");
        IO.output("ld");
        IO.output("echo [text]");
        IO.output("cd [directory]");
        IO.output("mkdir [directoryName]");
        IO.output("rmdir [directoryName]");
        IO.output("cfile [filename] [extension]");
        IO.output("write [filename]");
        IO.output("copy [source] [target]");
        IO.output("dfile [filename]");
        IO.output("destroy [filename]");
        IO.output("restore [filename]");
        IO.output("move [source] [filename]");
        IO.output("view [filename]");
        IO.output("");
        IO.output("logout");
    }

}

