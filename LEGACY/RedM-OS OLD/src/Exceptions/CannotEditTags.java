package Exceptions;

public class CannotEditTags extends RedMindException{
    public CannotEditTags(String details){
        super("RM-0025", "You cannot delete, add or modify any tags because that would compromise the integrity of the files. Only a DEV or ROOT user with access to the physical file can modify them.");
    }
}
