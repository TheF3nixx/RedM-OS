package otherThings;

import java.io.Serializable;

public class UserData implements Serializable{
    private static final long serialVersionUID = 1L;
    private String username;
    private String role;

    public UserData(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    

    @Override
    public String toString() {
        return "UserData{username='" + username + "', role='" + role + "'}";
    }
}
