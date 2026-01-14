package errors_exceptions;

public class UnauthorizedAccessException extends RedMindException {
    public UnauthorizedAccessException(String module) {
        super("Intento de acceso no autorizado detectado", 4001, module);
    }

    @Override
    public void handle() {
        System.out.println("[SECURITY ALERT] Informe generado para el núcleo de vigilancia.");
    }
}