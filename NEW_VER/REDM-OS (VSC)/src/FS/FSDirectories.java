package FS;

import IO.IO;
import Managers.ErrorHandler;
import System.SystemPaths;
import java.io.File;
import java.io.IOException;

public class FSDirectories {

    // Inicializa en la raíz virtual
    public FSDirectories() {
        SystemPaths.setCVP("/");
    }

    public static void pwd() {
        IO.output("[CurrentLocation]> " + SystemPaths.getCVP());
    }

    public static void cd(String name) {
        try {
            if (name.equals("<") || name.equals("..")) {
                back();
                return;
            }

            String currentVirtual = SystemPaths.getCVP();
            String targetVirtual = name.startsWith("/") ? name : (currentVirtual.equals("/") ? "/" + name : currentVirtual + "/" + name);

            RMFile targetReal = new RMFile(SystemPaths.toReal(targetVirtual));

            if (isInsideBase(targetReal.toFile()) && targetReal.exists() && targetReal.isDir()) {
                String newCVP = virtualPathFromReal(targetReal);
                SystemPaths.setCVP(newCVP);
                IO.output("[SYSTEM]> Moved into " + targetReal.getName());
            } else {
                ErrorHandler.trigger("009", name);
            }

        } catch (IOException e) {
            ErrorHandler.trigger("008", name);
        }
    }

    private static void back() {
        try {
            RMFile currentReal = new RMFile(SystemPaths.toReal(SystemPaths.getCVP()));
            RMFile rootReal = new RMFile(SystemPaths.getRealRoot());

            if (currentReal.toFile().getCanonicalPath().equals(rootReal.toFile().getCanonicalPath())) {
                IO.output("[SYSTEM]> Already at root.");
                return;
            }

            RMFile parent = currentReal.getParent();
            if (parent != null && isInsideBase(parent.toFile())) {
                String newCVP = virtualPathFromReal(parent);
                SystemPaths.setCVP(newCVP);
            } else {
                IO.output("[SYSTEM]> Already at root.");
            }

        } catch (IOException e) {
            IO.error("Couldn't go back.");
        }
    }

    public static void mkdir(String name) {
        RMFile newDir = new RMFile(SystemPaths.toReal(SystemPaths.getCVP())).resolve(name);
        if (newDir.exists()) {
            ErrorHandler.trigger("011", name);
            return;
        }
        if (newDir.mkdir()) IO.output("[SYSTEM]> Directory created: " + name);
        else ErrorHandler.trigger("010", name);
    }

    public static void rmdir(String name) {
        RMFile dir = new RMFile(SystemPaths.toReal(SystemPaths.getCVP())).resolve(name);
        if (!dir.exists() || !dir.isDir()) {
            ErrorHandler.trigger("010", name);
            return;
        }
        if (dir.delete()) IO.output("[SYSTEM]> Directory deleted: " + name);
        else ErrorHandler.trigger("013", name);
    }

    public static void ld() {
        RMFile current = new RMFile(SystemPaths.toReal(SystemPaths.getCVP()));
        RMFile[] contents = current.list();
        if (contents.length == 0) {
            IO.output("[SYSTEM]> Directory is empty.");
            return;
        }
        IO.output("[SYSTEM]> Listing contents of " + SystemPaths.getCVP() + ":");
        for (RMFile f : contents) {
            IO.output((f.isDir() ? "[DIR] " : "[FILE] ") + f.getName());
        }
    }

    private static boolean isInsideBase(File file) {
        try {
            String rootPath = SystemPaths.getRealRoot().getCanonicalPath();
            String filePath = file.getCanonicalPath();
            return filePath.startsWith(rootPath);
        } catch (IOException e) {
            return false;
        }
    }

    private static String virtualPathFromReal(RMFile realFile) throws IOException {
        String rootPath = SystemPaths.getRealRoot().getCanonicalPath();
        String filePath = realFile.toFile().getCanonicalPath();
        String relative = filePath.substring(rootPath.length());
        if (!relative.startsWith("/")) relative = "/" + relative;
        return SystemPaths.normalize(relative);
    }
}
