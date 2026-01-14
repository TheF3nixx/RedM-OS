package Shell;

import java.io.File;

public class SystemState {
    private static boolean isStable = false;
    private final static File baseDirectory = new File("redmind");//Esto guarda el directorio raíz en esa variable
    private static File currentDirectory = baseDirectory; //No sé por qué está esto así, pero funciona
    private static String currentUser; //Ya tiene soporte multiusuario

    public static boolean isStable() {
        return isStable;
    }

    public static void setStable(boolean stable) {
        isStable = stable;
    }

    public static File getCurrentDirectory() {
        return currentDirectory;
    }

    public static void setCurrentDirectory(File dir) {
        currentDirectory = dir;
    }

    public static File getBaseDirectory() {
        return baseDirectory;
    }

    public static String getCurrentUser() {
        return currentUser;
    }
}
