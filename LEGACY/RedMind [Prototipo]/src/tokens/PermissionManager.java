package tokens;

import java.util.*;

public class PermissionManager {
    private List<PermissionEntry> permissions = new ArrayList<>();

    public void addPermission(String path, String group, String user, Token perm) {
        PermissionEntry existing = findEntry(path, group, user);
        if (existing != null) {
            existing.updatePermission(perm);
        } else {
            permissions.add(new PermissionEntry(path, group, user, perm));
        }
    }

    public void removePermission(String path, String group, String user, Token perm) {
        PermissionEntry existing = findEntry(path, group, user);
        if (existing != null) {
            existing.revokePermission(perm);
        }
    }

    public void listPermissions(String path) {
        for (PermissionEntry entry : permissions) {
            if (path == null || entry.getPath().equals(path)) {
                System.out.println(entry.toDisplay());
            }
        }
    }

    public void grantPermissionToUser(String path, String fromUser, String toUser, Token perm) {
        // Aquí podrías controlar si "fromUser" tiene permiso para delegar
        addPermission(path, "shared", toUser, perm);
        System.out.println("Permiso concedido a " + toUser + " en " + path);
    }

    private PermissionEntry findEntry(String path, String group, String user) {
        for (PermissionEntry e : permissions) {
            if (e.getPath().equals(path) && e.getGroup().equals(group) && e.getUser().equals(user)) {
                return e;
            }
        }
        return null;
    }
}

