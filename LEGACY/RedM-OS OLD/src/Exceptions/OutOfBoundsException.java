package Exceptions;

public class OutOfBoundsException extends RedMindException{
    public OutOfBoundsException(String details){
        super("RM-0023", "The line you have attempted to reach is outside the limits set by the file");
    }
}
