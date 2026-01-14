package Managers;

import Core.ErrorHandler;
import Shell.Group;
import java.util.HashMap;
import java.util.HashSet;
import java.io.*;
import java.util.regex.Pattern;

public class GroupManager {
    private static HashMap<String, Group> groups = new HashMap<>();
    public static File GROUPS_FILE = new File("redmind/confidential/cdata/groups/groups.rmg");   

    //CARGAR LOS GRUPOS
    public static void loadGroups() {
        File file = GROUPS_FILE;
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            Group current = null;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                if (line.contains("::")) {
                    String[] parts = line.split(Pattern.quote("::"), 3);
                    if (parts.length >= 3) {
                        String name = parts[1].trim();
                        String desc = parts[2].trim();
                        current = new Group(name, desc);
                        groups.put(name, current);
                        //System.out.println("[DEBUG] Cargando grupo: " + name + " | Desc: " + desc);
                    }
                } else if (current != null) {
                    current.getMembers().add(line.trim());
                    //System.out.println("[DEBUG] Añadiendo miembro: " + line.trim() + " al grupo " + current.getName());
                }
            }

            //System.out.println("[DEBUG] Grupos cargados: " + groups.size());

        } catch (IOException e) {
            ErrorHandler.trigger("RM-0040");
        }
    }


    //GUARDAR EL ESTADO ACTUAL DE LOS GRUPOS
    public static void saveGroups() {
        File file = CommandManager.GROUPS_FILE;

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (Group g : groups.values()) {
                pw.println("::" + g.getName() + "::" + g.getDesc());
                for (String member : g.getMembers()) {
                    pw.println(member);
                }
                pw.println(); // Espacio entre grupos
            }
        } catch (IOException e) {
            System.err.println("Error al guardar los grupos: " + e.getMessage());
        }
    }


    //CREA 
    public static boolean createGroup(String name, String desc) {
        if (groups.containsKey(name)) return false;
        groups.put(name, new Group(name, desc));
        saveGroups();
        return true;
    }

    //BORRAR 
    public static boolean deleteGroup(String name) {
        if (!groups.containsKey(name)) return false;
        groups.remove(name);
        saveGroups();
        return true;
    }

    //AÑADE USUARIOS
    public static boolean addUserToGroup(String groupName, String user) {
        Group group = groups.get(groupName);
        if (group == null) return false;
        group.getMembers().add(user);
        saveGroups();
        return true;
    }

    //QUITA USUARIOS
    public static boolean removeUserFromGroup(String groupName, String user) {
        Group group = groups.get(groupName);
        if (group == null) return false;
        if (!isUserInGroup(groupName, user)){
            ErrorHandler.trigger("RM-0017");
            return false;
        }
        group.getMembers().remove(user);
        saveGroups();
        return true;
    }

    public static Group getGroup(String name) {
        return groups.get(name);
    }

    public static HashSet<String> listGroups() {
        return new HashSet<>(groups.keySet());
    }
    
    //COMPRUEBA SI EL USUARIO ESTÁ EN ESE GRUPO
    public static boolean isUserInGroup(String groupName, String user) {
        Group g = groups.get(groupName);
        return g != null && g.getMembers().contains(user);
    }
}



