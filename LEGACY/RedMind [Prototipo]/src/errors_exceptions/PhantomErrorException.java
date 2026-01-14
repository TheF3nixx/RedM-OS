package errors_exceptions;

public class PhantomErrorException extends RedMindException {
    public PhantomErrorException(String module) {
        super("Phamtom error without logic code", 9999, module);
    }

    @Override
    public void handle() {
        System.out.println("[???] The system doesn't know how to handle this. Just pray.");
    }
}
