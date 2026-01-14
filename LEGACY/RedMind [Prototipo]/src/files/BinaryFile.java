package files;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BinaryFile  {
    private final String path;

    public BinaryFile(String path) {
        this.path = path;
    }
    
    //GUARDAR CUALQUIER TIPO DE OBJETO SERIALIZABLE
    public void save(Object obj){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.path))){
            oos.writeObject(obj);
            System.out.println("[SYSTEM]> Saved correctly at " + this.path);
        }catch (Exception e){
            System.err.println("ERROR: [RM-0037: Could not save binary file]");
        }
    }
    
    //LEER CUALQUIER TIPO DE OBJETO SERIALIZABLE
    public Object read() throws IOException, ClassNotFoundException{
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.path))){
            return ois.readObject();
        }catch(FileNotFoundException ex){
            System.err.println("ERROR: [RM-0011: File not found or is a directory]");
        }catch(IOException | ClassNotFoundException exc){
            System.err.println("ERROR: [RM-0038: Could not read binary file]");
        }
        return null;
    }
    
    

    
}

