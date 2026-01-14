package Exceptions;

//Excepción base
public abstract class RedMindException extends Exception {
    private final String code;

    public RedMindException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}



