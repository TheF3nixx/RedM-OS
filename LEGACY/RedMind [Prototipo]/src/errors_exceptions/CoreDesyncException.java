package errors_exceptions;

public class CoreDesyncException extends RedMindException {
    public CoreDesyncException() {
        super("Desincronización entre núcleos. Inestabilidad crítica.", 6666, "CoreSystem");
    }

    @Override
    public void handle() {
        System.out.println("Forzando reencaje entre núcleos… puede provocar reinicio espontáneo.");
    }
}
