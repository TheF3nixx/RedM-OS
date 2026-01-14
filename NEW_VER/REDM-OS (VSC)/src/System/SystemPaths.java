package System;

import java.io.File;
import java.nio.file.Path;

public class SystemPaths {

    //Carpeta raíz REAL del sistema, totalmente independiente del proyecto
    private static final File REAL_ROOT = new File(System.getProperty("user.dir") + File.separator + "redmind");

    //Carpeta virtual actual (lo que ve el usuario en la terminal)
    private static String currentVirtualPath = "/";

    //--- RUTAS DE ACCESO ---
    public static File getRealRoot() {
        return REAL_ROOT;
    }

    public static String getCVP() {
        return currentVirtualPath;
    }

    public static void setCVP(String newPath) {
        if (!newPath.startsWith("/")) newPath = "/" + newPath;
        currentVirtualPath = normalize(newPath);
    }

    //Convierte una ruta virtual en una ruta real dentro del REAL_ROOT
    public static File toReal(String virtualPath) {
        if (!virtualPath.startsWith("/")) virtualPath = "/" + virtualPath;
        virtualPath = normalize(virtualPath);
        return new File(REAL_ROOT, virtualPath.substring(1));
    }

    //--- NORMALIZADOR ---
    public static String normalize(String path) {
        while (path.contains("//")) path = path.replace("//", "/");
        if (path.equals("")) return "/";
        return path;
    }

    //--- PAPELERA ---
    public static Path getTrashDir() {
        return getRealRoot().toPath().resolve("trash");
    }

    public static Path getTrashFilesDir() {
        return getTrashDir().resolve("files");
    }

    public static Path getTrashMetaDir() {
        return getTrashDir().resolve("meta");
    }

    //---ASEGURA QUE CREA TODAS LAS CARPETAS BASE---
    public static void ensureSystemStructure() {
        File root = getRealRoot();

        // Directorios básicos
        String[] dirs = {
            "crash",
            "logs",
            "programs",
            "system",
            "users",
            "tmp",
            "config",
            "safe",
        };

        // Crear cada uno
        for (String d : dirs) {
            File dir = new File(root, d);
            dir.mkdirs();

            // Subdirectorios especiales
            if (d.equals("system")) {
                new File(dir, "freeuse").mkdirs();
                new File(dir, "official").mkdirs();
                new File(dir, "restricted").mkdirs();
                new File(dir, "classified").mkdirs();
                new File(dir, "confidential").mkdirs();
                
            }

            if (d.equals("config")) {
                new File(dir, "user").mkdirs();
                new File(dir, "variables").mkdirs();
                new File(dir, "preferences").mkdirs();
            }

            if (d.equals("trash")){
                new File(dir, "files").mkdirs();
                new File(dir, "meta").mkdirs();
            }

        }
    }

}
