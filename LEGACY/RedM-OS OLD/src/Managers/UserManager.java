package Managers;

import Core.ErrorHandler;
import Exceptions.CannotImpersonateException;
import Exceptions.RedMindException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import Shell.AccessLvl;
import Shell.Shell;
import Shell.User;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
Esto es la clase que maneja todo lo relacionado con los usuarios físicos del sistema. 

Variables principales:

originalUser --> es el que se logueó primero antes de hacer un loginas
currentUser --> el que está usando la terminal actualmente
impersonatedUser --> por el que se está haciendo pasar
effectiveUser --> el que tiene el control real tras un passby
permissionLevel --> enum, almacena los posibles niveles de permiso
isImpersonating --> booleano que devuelve TRUE o FALSE en función si el usuario está haciendo passby

Métodos:

logout --> deja todas las variables a null y sale del sistema (termina el programa)
authenticateUser --> comprueba que el usuario con esa contraseña existe en el archivo users.rmu
search --> submétodo que busca en el nombre de usuario en el archivo
passby --> el usuario sigue en su sesión pero se hace pasar por otro
exitPassby --> deja de hacerse pasar por un usuario y vuelve a su sesión
loginAs --> se loguea como ese usuario (solo tras un passby)
logoutAs --> vuelve a la sesión anterior al nuevo logueo

*/

public class UserManager {
    private final static Scanner sc = new Scanner(System.in);
//VARIABLES
    private final static File USERS_FILE = new File("users/users.rmu");
    private final static HashMap<String, PermissionLevel> permissionLevels = new HashMap<>();
    private static HashMap<String, User> users = new HashMap<>();
    private static boolean isImpersonating = false;
     
    //Inicializamos los usuarios
    private static User effectiveUser = null;
    private static User currentUser = null;
    private static User originalUser = null;
    private static User impersonatedUser = null;
    
    //Getters y setters
    public static boolean isImpersonating() {
        return isImpersonating;
    }

    public static void setImpersonating(boolean isImpersonating) {
        UserManager.isImpersonating = isImpersonating;
    }

    public static User getOriginalUser() {
        return originalUser;
    }

    public static void setOriginalUser(User originalUser) {
        UserManager.originalUser = originalUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserManager.currentUser = currentUser;
    }

    public static User getImpersonatedUser() {
        return impersonatedUser;
    }

    public static void setImpersonatedUser(User impersonatedUser) {
        UserManager.impersonatedUser = impersonatedUser;
    }

    public static User getEffectiveUser() {
        return effectiveUser;
    }

    public static void setEffectiveUser(User effectiveUser) {
        UserManager.effectiveUser = effectiveUser;
    }
    
    public enum PermissionLevel{//Los posibles tipos de permisos que hay
        OPERATOR, ADMINISTRATOR, ROOT, DEVELOPER, ENGINEER;
        public static PermissionLevel fromString(String level){
            try{
                return PermissionLevel.valueOf(level.toUpperCase());//Devuelve el tipo de permiso de un usuario específico (GLOBAL)
            }catch (Exception e){
                return OPERATOR;
            }
        }
    }
    
    public static PermissionLevel getCurrentPermissionLevel() {
        if (effectiveUser == null)
            return null;

        // Directamente del objeto User
        return PermissionLevel.fromString(effectiveUser.getAc().name());
    }

    
    public static boolean isRoot(){//Comprueba si es ROOT
        return getCurrentPermissionLevel() == PermissionLevel.ROOT;
    }
    
    public static boolean isCurrentUserRoot(){//Comprueba si el usuario actual logueado es ROOT
        return currentUser != null && currentUser.getAc() == AccessLvl.ROOT;
    }
    
//-------------------------------------------------------------------------------//
//MÉTODOS GRALES
    public static void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 3) {
                    String username = parts[0];
                    String pass = parts[1];
                    AccessLvl level = AccessLvl.fromString(parts[2]);
                    User user = new User(username, pass, level);
                    users.put(username, user);
                }
            }
        } catch (IOException e) {
            ErrorHandler.trigger("RM-0003"); // Error al leer el archivo de usuarios
        }
    }

    public static void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User u : users.values()) {
                bw.write(u.getUsername() + ":" + u.getPassw() + ":" + u.getAc());
                bw.newLine();
            }
        } catch (IOException e) {
            ErrorHandler.trigger("RM-0012"); // Error al guardar usuarios
        }
    }
    
    public static boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassw().equals(password)) {
            currentUser = user;
            Shell.prompt = username + "#redm-os";
            return true;
        }
        return false;
    }
//-----------------------------------------------------------------------------------------------------//
//MÉTODOS CRÍTICOS
    public static boolean passby(String targetUsername) {
        User target = users.get(targetUsername);

        if (target == null || currentUser == null) return false; //Por si no hay nada
        
        //Comprobar que el usuario que pide hacer el passby es ROOT
        if(!isCurrentUserRoot()){
            ErrorHandler.trigger("RM-0016");
            return false;
        }
        
        //Comprobar que el usuario al que se quiere hacer el passby NO es ROOT
        if(target.getAc() == AccessLvl.ROOT){
            ErrorHandler.trigger("RM-0035");
            return false;
        }
        // No puedes hacer passby mientras estás en loginAs
        if (originalUser != null && currentUser != originalUser) {
            ErrorHandler.trigger("RM-0031"); // Already in loginAs session
            return false;
        }

        // No puedes hacerte pasar por ti mismo
        try{
            if (target.getUsername().equalsIgnoreCase(currentUser.getUsername())){
                throw new CannotImpersonateException("RM-0020");
            }
        }catch(RedMindException rme){
            ErrorHandler.trigger(rme);
            return false;
        }
        
        //Por si no encuentra al usuario
        if (target.getUsername().equalsIgnoreCase(null)){
            ErrorHandler.trigger("RM-0017");
            return false;
        }

        // Guardamos usuario real solo si no había ya un passby
        if (impersonatedUser == null) {
            originalUser = currentUser;
        }

        impersonatedUser = target;
        setEffectiveUser(users.get(targetUsername)); // nuevo usuario con el que operas
        setImpersonating(true);       

        // Prompt: [A(B)#redm-os]>
        Shell.prompt = originalUser.getUsername() + "(" + impersonatedUser.getUsername() + ")#redm-os";
        return true;
    }

    public static boolean loginAs(String targetUsername) {
        User targetUser = users.get(targetUsername);
        
        //Comprobar que el usuario que pide hacer el loginas es ROOT
        if(!isCurrentUserRoot()){
            ErrorHandler.trigger("RM-0016");
            return false;
        }

        // Solo puedes hacer loginAs si hiciste passby previamente
        if (impersonatedUser == null || originalUser == null ||
            !impersonatedUser.getUsername().equalsIgnoreCase(targetUsername)) {
            ErrorHandler.trigger("RM-0026"); // Passby required
            return false;
        }

        // Pedimos la contraseña del target
        System.out.print("[LOGINAS]> Enter the password for user " + targetUser.getUsername() + ": ");
        String inputPass = sc.nextLine();

        if (!targetUser.getPassw().equals(inputPass)) {
            ErrorHandler.trigger("RM-0027"); // Wrong password
            return false;
        }

        currentUser = targetUser;     // Cambio real
        setEffectiveUser(targetUser);  // este es ahora quien "opera"
        setImpersonating(true);        // sesión de suplantación activa
        // originalUser se mantiene como el dueño original
        impersonatedUser = null;      // Ya no es simulación

        // Prompt: [B@redm-os]>
        Shell.prompt = currentUser.getUsername() + "@redm-os";
        return true;
    }

    public static void logoutAs(String targetUsername) {
        // Solo válido si estamos en sesión loginAs, no solo passby
        if (originalUser != null && currentUser != null &&
            !currentUser.equals(originalUser)) {

            if (!currentUser.getUsername().equalsIgnoreCase(targetUsername)) {
                ErrorHandler.trigger("RM-0032"); // Not impersonating that user
                return;
            }

            currentUser = originalUser;
            setEffectiveUser(currentUser); // vuelves a ti
            setImpersonating(false);       // se terminó la suplantación
            originalUser = null;
            impersonatedUser = null;

            if (currentUser.getAc() == AccessLvl.ROOT) {
                Shell.prompt = currentUser.getUsername() + "#redm-os";
            } else {
                Shell.prompt = currentUser.getUsername() + "@redm-os";
            }
        } else {
            ErrorHandler.trigger("RM-0019"); // Not in loginAs session
        }
    }

    public static void exitPassby() {
        // No puedes salir de passby si estás en loginAs (ya eres ese user)
        if (impersonatedUser != null && originalUser != null &&
            currentUser.equals(originalUser)) {

            currentUser = originalUser;
            impersonatedUser = null;
            originalUser = null;
            setEffectiveUser(currentUser);   // vuelves a operar como tú
            setImpersonating(false);         // ya no estás suplantando

            if (currentUser.getAc() == AccessLvl.ROOT) {
                Shell.prompt = currentUser.getUsername() + "#redm-os";
            } else {
                Shell.prompt = currentUser.getUsername() + "@redm-os";
            }
        } else {
            ErrorHandler.trigger("RM-0032"); // No active passby session
        }
    }
//-----------------------------------------------------------------------------------------------------//    
    
    public static void uivs() {
        Shell.delay(2000);
        System.out.println("[UIVS] :: User Integrity Verification System");
        System.out.println("---------------------------------------------------");
        Shell.delay(1000);

        User curr = getCurrentUser();
        User imp = getImpersonatedUser();
        User eff = getEffectiveUser();
        User og = getOriginalUser();
        boolean impersonating = isImpersonating();
        PermissionLevel pl = getCurrentPermissionLevel();

        System.out.println("> Current user........: " + (curr != null ? curr.getUsername() : "NONE"));
        Shell.delay(400);
        System.out.println("> Effective user......: " + (eff != null ? eff.getUsername() : "NONE"));
        Shell.delay(400);
        System.out.println("> Impersonation.......: " + (impersonating ? "ACTIVE" : "NOT ACTIVE"));
        Shell.delay(400);
        System.out.println("> Impersonated user...: " + (imp != null ? imp.getUsername() : "NONE"));
        Shell.delay(400);
        System.out.println("> Original root user..: " + (og != null ? og.getUsername() : "NONE"));
        Shell.delay(400);
        System.out.println("> Permission level....: " + (pl != null ? pl.name() : "UNKNOWN"));
        System.out.println("---------------------------------------------------");
        Shell.delay(400);

        if (impersonating) {
            if (og != null && !curr.equals(og)) {
                System.out.println("[UIVS]> You are operating under an impersonation session.");
            } else {
                System.out.println("[UIVS]> You are impersonating a user temporarily (passby).");
            }
        } else {
            System.out.println("[STATUS]> Session integrity OK.");
        }

        Shell.delay(400);
        System.out.println();
    }    
    
    //Permisos para acceder a los directorios
    public static boolean canAccessDirectory(String directoryName){//Restringe el acceso a directorios de manera individual
        PermissionLevel pl = getCurrentPermissionLevel();
        //Este método es más tedioso, pero es mucho más seguro que hacerlo generalizado, por lo que pueda pasar
        switch (directoryName.toLowerCase()) {
            case "freeuse":
            case "official":
            case "redmind": 
            case "files":
                return true; // Acceso universal
            case "restricted":
            case "classified":
                return pl == PermissionLevel.ADMINISTRATOR || pl == PermissionLevel.ROOT;
            case "confidential":
                return pl == PermissionLevel.ROOT;
            default:
                return false; // Directorio no reconocido
        }
    }
    
//UTILIDADES
    public static boolean userExists(String username) {
        return users.containsKey(username);
    }

    public static User getUser(String username) {
        return users.get(username);
    }
    
     public static void addUser(User u) {
        users.put(u.getUsername(), u);
        saveUsers();
    }

    public static void deleteUser(String username) {
        users.remove(username);
        saveUsers();
    }
    

}//class
