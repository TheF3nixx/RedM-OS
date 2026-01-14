package errors_exceptions;

public class ExecutionTimeoutException extends RedMindException {
    public ExecutionTimeoutException(String module) {
        super("Tiempo de espera excedido en ejecución de protocolo", 3002, module);
    }

    @Override
    public void handle() {
        System.out.println("Finalizando proceso manualmente. Analiza logs del sistema.");
    }
}
