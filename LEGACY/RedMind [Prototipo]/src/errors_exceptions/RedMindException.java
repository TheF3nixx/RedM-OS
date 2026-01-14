package errors_exceptions;

public abstract class RedMindException extends Exception {
    protected int errorCode;
    protected String originModule;

    public RedMindException(String message, int errorCode, String originModule) {
        super(message);
        this.errorCode = errorCode;
        this.originModule = originModule;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getOriginModule() {
        return originModule;
    }

    public abstract void handle();  // comportamiento específico de cada error
}

