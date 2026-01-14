package Exceptions;

public class UnableToReadException extends RedMindException{
    public UnableToReadException(String detail){
        super("RM-0003", "Unable to read file. It may be corrupt, restricted, or in an unsupported format.");
    }
}
