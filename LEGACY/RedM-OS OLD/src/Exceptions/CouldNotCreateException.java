package Exceptions;

public class CouldNotCreateException extends RedMindException{
    public CouldNotCreateException(String detail){
        super("RM-0010", "Could not create file. Possible causes: insufficient permissions, invalid filename, path does not exist or file already exists.");
    }
}
