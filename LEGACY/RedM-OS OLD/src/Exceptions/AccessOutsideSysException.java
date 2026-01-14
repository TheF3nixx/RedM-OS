package Exceptions;

public class AccessOutsideSysException extends RedMindException{
    public AccessOutsideSysException(String detail){
        super("RM-0006", "You tried to reach a directory outside the RedMind virtual system root.");
    }
}
