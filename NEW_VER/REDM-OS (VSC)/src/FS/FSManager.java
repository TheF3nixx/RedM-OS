package FS;

import IO.IO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FSManager {
    private static final List<String> history = new ArrayList<>();
    private static final Map<String, String> aliases = new HashMap<>();

    public static void pwd(){
        FSDirectories.pwd();
    }

    public static void cd(String target){
        FSDirectories.cd(target);
    }

    public static void ld(){
        FSDirectories.ld();
    }

    public static void mkdir(String dirName){
        FSDirectories.mkdir(dirName);
    }

    public static void rmdir(String dirName){
        FSDirectories.rmdir(dirName);
    }

    public static void cfile(String filename, String ext){
        FSFiles.createFile(filename, ext);
    }

    public static void dfile(String filename){
        FSFiles.deleteFile(filename);
    }

    public static void dtfile(String filename){
        FSFiles.destroyFile(filename);
    }

    public static void rfile(String filename){
        FSFiles.restoreFile(filename);
    }

    public static void copy(String source, String destiny){
        FSFiles.copyFile(source, destiny);
    }

    public static void move(String source, String destiny){
        FSFiles.moveFile(source, destiny);
    }

    public static void view(String filename){
        FSFiles.viewFile(filename);
    }

    public static void edit(String filename){
        FSFiles.createEditableFile(filename);
    }

    //MÉTODOS PROPIOS DE FSMANAGER

    //Valida nombres de archivos
    public static boolean isValidName(String filename){
        return filename != null && !filename.isEmpty() && !filename.matches(".*[<>:\\\"|?*].*");
    }

    //Historial
    public static void add(String command){
        history.add(command);
    }

    public static void show(){
        for (int i = 0; i < history.size(); i++) {
            IO.output((i+1) + ": " + history.get(i));
        }
    }

    //Alias para archivos
    public static void setAlias(String name, String path){
        aliases.put(name, path);
    }

    public static String resolve(String name){
        return aliases.get(name);
    }

    //Normalizador de rutas
    public static String normalize(String path) {
        if (path == null || path.isEmpty()) return "/";
        path = path.replace("\\", "/");

        String[] parts = path.split("/");
        StringBuilder result = new StringBuilder();

        for (String p : parts) {
            if (p.isEmpty() || p.equals(".")) continue;
            if (p.equals("..")) {
                int i = result.lastIndexOf("/");
                if (i > 0) result.delete(i, result.length());
                continue;
            }
            result.append("/").append(p);
        }

        return result.length() == 0 ? "/" : result.toString();
    }

    //Información completa de un archivo
    public static void info(RMFile file) {
        if (!file.exists()) {
            IO.error("[ERROR: RM-019]> File does not exist: " + file);
            return;
        }

        IO.output("Name: " + file.getName());
        IO.output("Type: " + (file.isDir() ? "Directory" : "Archive"));
        IO.output("Size: " + file.size() + " bytes");
        IO.output("Last modification: " + new java.util.Date(file.lastModified()));
    }

}
