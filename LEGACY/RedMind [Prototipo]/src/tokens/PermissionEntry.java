/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tokens;

/**
 *
 * @author Usuario
 */
public class PermissionEntry {
    private String path;
    private String group;
    private String user;
    private Token permission;

    public PermissionEntry(String path, String group, String user, Token permission) {
        this.path = path;
        this.group = group;
        this.user = user;
        this.permission = permission;
    }

    public String getPath() { return path; }
    public String getGroup() { return group; }
    public String getUser() { return user; }
    public Token getPermission() { return permission; }

    public void updatePermission(Token newPerm) {
        this.permission.merge(newPerm);
    }

    public void revokePermission(Token toRemove) {
        this.permission.remove(toRemove);
    }

    public String toDisplay() {
        return String.format("[%s] --- [%s] --- [%s] --- %s",
                permission.toTokenString(), group, user, path);
    }
}

