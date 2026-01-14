package Shell;

import Core.ErrorHandler;
import Managers.CommandManager;
import Core.SystemCore;
import Managers.GroupManager;
import Managers.LogManager;
import Managers.UserManager;
import java.io.File;
import java.util.*;

public class Shell {
    static Scanner sc = new Scanner(System.in);
    public static String prompt = "user@redm-os";//Este es el prompt por defecto
    public static File GROUPS_FILE = new File("redmind/confidential/cdata/groups/groups.rmg");   
    
    public static void main(String[] args) {
    System.out.println("RedMind Industries - Terminal Boot Module");
    System.out.println("You must insert a valid boot code in order to access the program.");
    boolean bootedCorrectly = false;
    while(!bootedCorrectly){
        System.out.print("> ");
        String input = sc.nextLine().trim();
        switch(input){
            case "boot -override -protocol ZMX-0921-acc": 
                SystemCore.bootSequence();
                bootedCorrectly = true;
                break;
            case "fastboot": 
                bootedCorrectly = true;
                break;
            default:
                ErrorHandler.trigger("RM-0041");
        }
    }
    
    System.out.println("");
    System.out.println("To use the terminal you must log in with a valid username and password.");

    int atts = 0;
    final int MAX_ATTS = 3;
    boolean logged = false;

    UserManager.loadUsers(); // Cargamos los usuarios desde el archivo

    while (!logged && atts < MAX_ATTS) {
        System.out.print("[LOGIN]> Username: ");
        String username = sc.nextLine().trim();
        System.out.print("[LOGIN]> Password: ");
        String passw = sc.nextLine().trim();

        if (UserManager.authenticateUser(username, passw)) {
            logged = true;

            User u = UserManager.getUser(username); // Recuperamos el objeto User

            UserManager.setCurrentUser(u);               // Establece el usuario como actual
            UserManager.setEffectiveUser(u);             // También como efectivo por defecto

            System.out.println("Welcome, " + u.getUsername() + " (" + u.getAc() + ")");
            LogManager.log("User " + u.getUsername() + " entered the system");

            // Establecemos el prompt visual
            if (u.getAc() == AccessLvl.ROOT) {
                prompt = u.getUsername() + "#redm-os"; // root: hashtag
            } else {
                prompt = u.getUsername() + "@redm-os"; // normal/administrador: arroba
            }

        } else {
            atts++;
            ErrorHandler.trigger("RM-0015");
            if (atts >= MAX_ATTS) {
                System.err.println("Too many failed attempts. Exiting system...");
                delay(1000);
                LogManager.log("The user has been locked out of the system after entering the wrong credentials three times in a row.");
                System.exit(0);
            }
        }
    }

    // Si haces el login correcto, te deja pasar a la terminal
    System.out.println("\u001B[32m[ACCESS GRANTED]\u001B[0m");
    SystemCore.initialize();
    delay(1000);
    CommandManager.initializeCommands();//Carga comandos
    GroupManager.loadGroups();//Carga los grupos
    delay(2000);

    System.out.println("-------------------------------------------------");
    System.out.println("Type '-help' to see available commands.\n");
    System.out.println("[REDMIND TERMINAL | V2.1.0]");

    while (true) {
        System.out.print("[" + prompt + "]> ");
        String input = sc.nextLine().trim();
        if (input.isEmpty()) continue;
        CommandManager.executeCommand(input);
    }
}

//-------------------------------------------------------------------------------------------//
    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("INTERRUPT EXCEPTION: System instability detected.");
        }
    }
}
