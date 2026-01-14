package errors_exceptions;

public class MemoryOverflowException extends RedMindException {
    public MemoryOverflowException(String module) {
        super("Desbordamiento de memoria en módulo activo", 2001, module);
    }

    @Override
    public void handle() {
        System.out.println("[ALERT] Vaciar cachés y reiniciar subsistemas relacionados.");
    }
}