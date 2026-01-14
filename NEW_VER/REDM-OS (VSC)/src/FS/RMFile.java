package FS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public class RMFile {
    private final File realFile;
    private final Date timestamp;

    // --- CONSTRUCTORES ---
    public RMFile(File file, Date timestamp) {
        this.realFile = file;
        this.timestamp = timestamp;
    }

    public RMFile(String absolutePath) {
        this.realFile = new File(absolutePath);
        this.timestamp = new Date();
    }

    public RMFile(File file) {
        this.realFile = file;
        this.timestamp = new Date();
    }

    // --- GETTERS ---
    public String getName() {
        return realFile.getName();
    }

    public RMFile getParent() {
        File p = realFile.getParentFile();
        return (p != null) ? new RMFile(p) : null;
    }

    public String getAbsPath() {
        return realFile.getAbsolutePath();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    // --- PROPIEDADES ---
    public boolean exists() {
        return realFile.exists();
    }

    public boolean isDir() {
        return realFile.isDirectory();
    }

    public boolean isFile() {
        return realFile.isFile();
    }

    public long size() {
        return realFile.length();
    }

    public long lastModified() {
        return realFile.lastModified();
    }

    public File toFile() {
        return realFile;
    }

    public Path toPath(){//Necesario para la papelera de reciclaje
        return realFile.toPath();
    }

    public Path toAbsolutePath(){//Necesario para la papelera de reciclaje
        return realFile.getAbsoluteFile().toPath().normalize();
    }


    // --- OPERACIONES ---
    public boolean delete() {
        return realFile.delete();
    }

    public boolean mkdir() {
        return realFile.mkdir();
    }

    public RMFile resolve(String filename) {
        return new RMFile(new File(realFile, filename));
    }

    public void copyTo(RMFile dest) throws IOException {
        Files.copy(realFile.toPath(), dest.realFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public void moveTo(RMFile dest) throws IOException {
        Files.move(realFile.toPath(), dest.realFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public RMFile[] list() {
        if (!realFile.isDirectory()) return new RMFile[0];
        File[] list = realFile.listFiles();
        if (list == null) return new RMFile[0];

        RMFile[] out = new RMFile[list.length];
        for (int i = 0; i < list.length; i++)
            out[i] = new RMFile(list[i]);

        return out;
    }

    @Override
    public String toString() {
        return "[RMFile] " + getAbsPath() + " (ts=" + timestamp.getTime() + ")";
    }
}
