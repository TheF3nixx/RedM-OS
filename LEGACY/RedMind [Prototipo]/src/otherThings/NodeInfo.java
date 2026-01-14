package otherThings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class NodeInfo implements Serializable {
    private String name;
    private String status;
    private int powerLevel;

    public NodeInfo(String name, String status, int powerLevel) {
        this.name = name;
        this.status = status;
        this.powerLevel = powerLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }
    
    public static void saveNodes(ArrayList<NodeInfo> nodes, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(nodes);
            System.out.println("Datos guardados en " + filename);
        } catch (Exception e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }
    
    public static ArrayList<NodeInfo> loadNodes(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<NodeInfo>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Error al leer: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        return "Node: " + name + " | Status: " + status + " | Power: " + powerLevel;
    }
}

