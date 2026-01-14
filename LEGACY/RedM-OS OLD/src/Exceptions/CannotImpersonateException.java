package Exceptions;

public class CannotImpersonateException extends RedMindException{
    public CannotImpersonateException(String detail){
        super("RM-0020", "The 'passby' command must be used with another user, as you cannot impersonate yourself for logical reasons");
    }
}
