package Exceptions;
//Esta es la excepción de directorios, no de archivos
public class PermissionDeniedException extends RedMindException{
    public PermissionDeniedException(String detail){
        super("RM-0021", "You don't have enough privileges to access the directory '" + detail + "'. Try again from a user with a higher level.");
    }
}
