package Managers;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import Shell.Commands.*;
import Shell.Commands.ComplexComms.*;

import System.*;
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
            ErrorHandler.trigger(ErrorC.UNKNOWN_COMMAND, input);
            return;
        }

        c.execute(p.args(), p.flags());
    }

    public static Command getComplexCommand(String name) {
        return complex.get(name);
    }

    public static boolean isSimpleCommand(String name){
        return simple.containsKey(name);
    }

    public static Set<String> getAllCommandNames(){
        Set<String> all = new TreeSet<>();
        all.addAll(simple.keySet());
        all.addAll(complex.keySet());
        return all;
    }

    //LOS COMANDOS EN SÍ

    public static void init(){
        //COMANDOS SIN ARGUMENTOS
        register("-help", () -> {
            help();
        });
        register("sysinfo", () -> {
            SystemInfo.getAllInfo();
        });
        register("motd", () -> {
            SystemMisc.motd();
        });
        register("idle", () -> {
            IO.pause("[SYSTEM]> Session paused.");;
        });
        register("clear", () -> {
            IO.clear();
        });
        register("date", () -> {
            SystemMisc.date();
        });
        register("uptime", () -> {
            IO.output("Uptime (seconds): " + SystemState.getUptime());
        });
        register("sysmode", () -> {
            IO.output("Current system mode: " + SystemState.getMode());
        });
        register("wd", () -> {
            FS.FSDirectories.pwd();
        });
        register("ld", () -> {
            FS.FSDirectories.ld();
        });
        register("status", () -> {
            SystemStatus.execute();//wip
        });
        register("logout", () -> {
            IO.pulseLoader("Shutting down...", 30, 120);
            IO.output("[SYSTEM]> Shutted down successfully.");
            System.exit(0);
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
        register(new HandBook());
        register(new Toggle());

        //COMANDOS INÚTILES PERO FÁSILES
        register(new Echo());

    }

    public static void help(){
        IO.output("-----AVAILABLE COMMANDS: 19-----");
        IO.output("--GENERAL--");
        IO.output("sysinfo");
        IO.output("motd");
        IO.output("idle");
        IO.output("clear");
        IO.output("date");
        IO.output("uptime");
        IO.output("sysmode");
        IO.output("wd");
        IO.output("ld");
        IO.output("echo [text]");
        IO.output("hb [command]");//se irá modificando a medida que se vayan añadiendo comandos/flags
        IO.output("status");//se irá "engordando" conforme se vayan metiendo features
        IO.output("--DIRECTORIES AND FILES--");
        IO.output("cd [directory]");
        IO.output("mkdir [directoryName]");
        IO.output("rmdir [directoryName]");
        IO.output("cfile [filename] [extension]");
        IO.output("write [filename]");
        IO.output("rep [source] [target]");
        IO.output("dfile [filename]");
        IO.output("destroy [filename]");
        IO.output("restore [filename]");
        IO.output("shift [source] [filename]");
        IO.output("view [filename]");
        IO.output("--MISC--");
        IO.output("toggle [something] ON/OFF");//se irá modificando a medida que se vayan añadiendo comandos
        IO.output("logout");
    }

}

