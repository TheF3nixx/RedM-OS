package Shell;

import java.util.HashSet;

public class Group {
    private String name;
    private final HashSet<String> members;
    private String desc;

    public Group(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.members = new HashSet<>();
    }
    
    public Group(String name) {
        this(name, "No description provided.");
    }

    public String getName() {
        return name;
    }

    public HashSet<String> getMembers() {
        return members;
    }

    public String getDesc() {
        return desc;
    }

    public void addMember(String username) {
        members.add(username);
    }

    public void removeMember(String username) {
        members.remove(username);
    }

    public boolean hasMember(String username) {
        return members.contains(username);
    }
}

