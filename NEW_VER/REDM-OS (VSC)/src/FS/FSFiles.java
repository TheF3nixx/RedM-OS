package FS;

import IO.IO;
import Managers.ErrorHandler;
import System.ErrorC;
import System.SystemPaths;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FSFiles {
    private static final Path TRASH_FILES = SystemPaths.getTrashFilesDir();
    private static final Path TRASH_META  = SystemPaths.getTrashMetaDir();

    //---- Helper: Se asegura de que siempre exista la papelera DENTRO de la raíz
    private static void ensureTrash() throws IOException {
        Files.createDirectories(SystemPaths.getTrashFilesDir());
        Files.createDirectories(SystemPaths.getTrashMetaDir());
    }

    // ---- Helper: Resuelve una ruta virtual/relativa a RMFile dentro del sandbox
    private static RMFile resolveToRMFile(String virtualOrRelative) {
        if (virtualOrRelative == null || virtualOrRelative.isEmpty()) {
            return new RMFile(SystemPaths.toReal(SystemPaths.getCVP()));
        }

        String path;
        if (virtualOrRelative.startsWith("/")) {
            path = SystemPaths.normalize(virtualOrRelative);
        } else {
            String cvp = SystemPaths.getCVP();
            path = cvp.equals("/") ? "/" + virtualOrRelative : cvp + "/" + virtualOrRelative;
            path = SystemPaths.normalize(path);
        }
        return new RMFile(SystemPaths.toReal(path));
    }

    public static boolean exists(String name) {
        return resolveToRMFile(name).exists();
    }

    public static void createFile(String name, String ext) {
        if (name == null || ext == null) {
            ErrorHandler.trigger(ErrorC.INVALID_PARAMETER_FOR_FILE, name);
            return;
        }

        String filename = name.endsWith("." + ext) ? name : (name + "." + ext);
        RMFile file = new RMFile(SystemPaths.toReal(SystemPaths.getCVP())).resolve(filename);

        try {
            if (file.exists()) {
                ErrorHandler.trigger(ErrorC.FILE_ALREADY_EXISTS, filename);
                return;
            }
            if (file.toFile().createNewFile()) {
                IO.output("[SYSTEM]> File created: " + filename);
            } else {
                ErrorHandler.trigger(ErrorC.FAILED_TO_CREATE_FILE, filename);
            }
        } catch (IOException e) {
            ErrorHandler.trigger(ErrorC.FAILED_TO_CREATE_FILE, filename);
        }
    }

    public static void createEditableFile(String filename) {
        if (filename == null) {
            ErrorHandler.trigger(ErrorC.INVALID_PARAMETER_FOR_FILE, filename);
            return;
        }

        RMFile file = new RMFile(SystemPaths.toReal(SystemPaths.getCVP())).resolve(filename);
        Path path = file.toFile().toPath();

        try {
            if (!file.exists()) {
                file.toFile().createNewFile();
                IO.output("[SYSTEM]> File created: " + filename);
            } else {
                IO.output("[EDITOR]> Editing: " + filename);
            }

            IO.output("[EDITOR]> Write content. ':wq' to save, ':q' to exit without saving");
            IO.output("------------------------------------------------------------");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            List<String> buffer = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.equals(":wq")) {
                    Files.write(path, buffer);
                    IO.output("[EDITOR]> File saved.");
                    break;
                }
                if (line.equals(":q")) {
                    IO.output("[EDITOR]> Changes discarded.");
                    break;
                }
                buffer.add(line);
            }

        } catch (IOException e) {
            ErrorHandler.trigger(ErrorC.FAILED_TO_ACCESS_FILE, filename);
        }
    }

    public static void deleteFile(String filename) {

        RMFile file = resolveToRMFile(filename);

        try {
            if (!file.exists() || !file.isFile()) {
                ErrorHandler.trigger(ErrorC.FILE_DOES_NOT_EXIST, filename);
                return;
            }

            ensureTrash();

            Path originalPath = file.toAbsolutePath();
            String trashName = System.currentTimeMillis() + "_" + file.getName();

            Path trashFile = TRASH_FILES.resolve(trashName).toAbsolutePath();
            Path metaFile  = TRASH_META.resolve(trashName + ".meta").toAbsolutePath();

            Files.move(originalPath, trashFile, StandardCopyOption.REPLACE_EXISTING);

            Files.write(metaFile, List.of(
                "originalPath=" + originalPath.toString(),
                "deletedAt=" + System.currentTimeMillis()
            ));

            IO.output("[SYSTEM]> File moved to trash.");

        } catch (Exception e) {
            IO.output("[DEBUG] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            ErrorHandler.trigger(ErrorC.COULD_NOT_MOVE_FILE, filename);
        }
    }


    public static void destroyFile(String filename){
        RMFile file = resolveToRMFile(filename);

        if (!file.exists() || !file.isFile()) {
            ErrorHandler.trigger(ErrorC.FILE_DOES_NOT_EXIST, filename);
            return;
        }

        if (file.delete()) {
            IO.output("[SYSTEM]> File successfully deleted.");
        } else {
            ErrorHandler.trigger(ErrorC.COULD_NOT_MOVE_FILE, filename);
        }
    }

    public static void restoreFile(String trashName) {
        try {
            ensureTrash();

            Path trashFile = TRASH_FILES.resolve(trashName);
            Path metaFile  = TRASH_META.resolve(trashName + ".meta");
            

            if (!Files.exists(trashFile) || !Files.exists(metaFile)) {
                ErrorHandler.trigger(ErrorC.FILE_DOES_NOT_EXIST, trashName);
                return;
            }

            //Leer metadatos
            List<String> meta = Files.readAllLines(metaFile);
            String originalPath = null;

            for (String line : meta) {
                if (line.startsWith("originalPath=")) {
                    originalPath = line.substring("originalPath=".length());
                    break;
                }
            }

            if (originalPath == null) {
                ErrorHandler.trigger(ErrorC.INVALID_SOURCE, trashName);
                return;
            }

            Path target = Paths.get(originalPath).toAbsolutePath().normalize();

            //Crear directorio si ya no existe
            if (target.getParent() != null && !Files.exists(target.getParent())) {
                Files.createDirectories(target.getParent());
            }

            Files.move(trashFile, target, StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(metaFile);

            IO.output("[SYSTEM]> File restored to original location.");

        } catch (IOException e) {
            ErrorHandler.trigger(ErrorC.COULD_NOT_MOVE_FILE, trashName);
        }
    }

    public static void copyFile(String source, String dest) {
        RMFile src = resolveToRMFile(source);
        RMFile dst = resolveToRMFile(dest);

        if (!src.exists() || !src.isFile()) {
            ErrorHandler.trigger(ErrorC.INVALID_SOURCE, source);
            return;
        }

        try {
            if (dst.exists() && dst.isDir()) {
                RMFile target = dst.resolve(src.getName());
                src.copyTo(target);
                IO.output("[SYSTEM]> Copied: " + source + " → " + target.getName());
                return;
            }

            RMFile parent = dst.getParent();
            if (parent != null && parent.exists() && parent.isDir()) {
                src.copyTo(dst);
                IO.output("[SYSTEM]> Copied: " + source + " → " + dst.getName());
                return;
            }

            ErrorHandler.trigger(ErrorC.COULD_NOT_COPY, dest);
        } catch (IOException e) {
            ErrorHandler.trigger(ErrorC.COULD_NOT_COPY, source);
        }
    }

    public static void moveFile(String source, String dest) {
        RMFile src = resolveToRMFile(source);
        RMFile dst = resolveToRMFile(dest);

        if (!src.exists() || !src.isFile()) {
            ErrorHandler.trigger(ErrorC.INVALID_SOURCE, source);
            return;
        }

        try {
            if (dst.exists() && dst.isDir()) {
                RMFile target = dst.resolve(src.getName());
                src.moveTo(target);
                IO.output("[SYSTEM]> Moved: " + source + " → " + target.getName());
                return;
            }

            RMFile parent = dst.getParent();
            if (parent != null && parent.exists() && parent.isDir()) {
                src.moveTo(dst);
                IO.output("[SYSTEM]> Moved: " + source + " → " + dst.getName());
                return;
            }

            ErrorHandler.trigger(ErrorC.COULD_NOT_MOVE_FILE, dest);
        } catch (IOException e) {
            ErrorHandler.trigger(ErrorC.COULD_NOT_MOVE_FILE, dest);
        }
    }

    public static void viewFile(String filename) {
        RMFile file = resolveToRMFile(filename);

        if (!file.exists() || !file.isFile()) {
            ErrorHandler.trigger(ErrorC.FILE_DOES_NOT_EXIST, filename);
            return;
        }

        try {
            Files.lines(file.toFile().toPath()).forEach(IO::output);
        } catch (IOException e) {
            ErrorHandler.trigger(ErrorC.FAILED_TO_ACCESS_FILE, filename);
        }
    }
}
