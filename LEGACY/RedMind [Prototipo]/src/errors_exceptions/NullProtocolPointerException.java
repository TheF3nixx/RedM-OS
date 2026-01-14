package errors_exceptions;

public class NullProtocolPointerException extends RedMindException {
    public NullProtocolPointerException(String module) {
        super("Referencia a protocolo inexistente", 2002, module);
    }

    @Override
    public void handle() {
        System.out.println("[DEBUG] Referencia inválida. Recompila los módulos afectados.");
    }
}
