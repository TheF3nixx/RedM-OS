package security;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class secureDirectory implements Serializable {
    private String path;
    private boolean isLocked;
    private String password;
    private String requiredAccessLevel;
    private List<String> accessLogs;

    public secureDirectory(String path, String requiredAccessLevel) {
        this.path = path;
        this.requiredAccessLevel = requiredAccessLevel;
        this.isLocked = false;
        this.accessLogs = new ArrayList<>();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRequiredAccessLevel() {
        return requiredAccessLevel;
    }

    public void setRequiredAccessLevel(String requiredAccessLevel) {
        this.requiredAccessLevel = requiredAccessLevel;
    }

    public List<String> getAccessLogs() {
        return accessLogs;
    }

    public void setAccessLogs(List<String> accessLogs) {
        this.accessLogs = accessLogs;
    }

    public boolean unlock(String pass) {
        if (this.password != null && this.password.equals(pass)) {
            isLocked = false;
            return true;
        }
        return false;
    }

    public void logAccess(String user) {
        accessLogs.add(LocalDateTime.now() + " - " + user);
    }
    
}

