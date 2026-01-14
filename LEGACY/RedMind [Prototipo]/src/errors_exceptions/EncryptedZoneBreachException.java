package errors_exceptions;

public class EncryptedZoneBreachException extends RedMindException {
    public EncryptedZoneBreachException(String module) {
        super("Violación de zona encriptada", 4002, module);
    }

    @Override
    public void handle() {
        System.out.println("[LOCKDOWN] Activando protocolos de contención...");
    }
}
