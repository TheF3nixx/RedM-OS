package errors_exceptions;

public class FileCorruptionException extends RedMindException {
    public FileCorruptionException(String module) {
        super("Archivo dañado o ilegible", 1001, module);
    }

    @Override
    public void handle() {
        System.out.println("[CRITICAL] Intentando restaurar desde backup...");
        // lógica de restauración simulada
    }
}