package errors_exceptions;

public class CommandNotFoundException extends RedMindException {
    public CommandNotFoundException(String command) {
        super("Comando no reconocido: " + command, 3001, "CommandManager");
    }

    @Override
    public void handle() {
        System.out.println("¿Querías decir 'help'? Usa 'list-commands' para ver los disponibles.");
    }
}
