package Shell;

import java.util.HashSet;

public class User {
    private String username;
    private String passw;
    private AccessLvl ac;
    private HashSet<String> groups = new HashSet<>();

    //CONSTRUCTORES
    public User(String username, String passw, AccessLvl ac, HashSet<String> groups) {
        this.username = username;
        this.passw = passw;
        this.ac = ac;
        this.groups = groups != null ? groups : new HashSet<>();
    }

    public User(String username, String passw, AccessLvl ac) {
        this.username = username;
        this.passw = passw;
        this.ac = ac;
    }

    public User(String username, String passw) {
        this.username = username;
        this.passw = passw;
    }
    
    //NOMBRE DE USUARIO
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    //CONTRASEÑA
    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }
    
    //NIVEL DE ACCESO
    public AccessLvl getAc() {
        return ac;
    }

    public void setAc(AccessLvl ac) {
        this.ac = ac;
    }
    
    //GRUPOS
    public HashSet<String> getGroups() {
        return groups;
    }

    public void setGroups(HashSet<String> groups) {
        this.groups = groups;
    }
    
    public void addToGroup(String grName){
        if (!groups.contains(grName)) groups.add(grName);
    }
    
    public void removeFromGroup(String grName){
        groups.remove(grName);  
    }
    
    public boolean isInGroup(String grName){
        return groups.contains(grName);
    }

    @Override
    public String toString() {
        return "User -- " + "Level: " + ac + " -- Groups: " + groups;
    }
    
    
    
}
