package errors_exceptions;

public class FileAccessDeniedException extends RedMindException {
    public FileAccessDeniedException(String module) {
        super("Permisos insuficientes para acceder al archivo", 1002, module);
    }

    @Override
    public void handle() {
        System.out.println("Acceso denegado. Intenta en Modo Ingeniero o Desarrollador.");
    }
}
