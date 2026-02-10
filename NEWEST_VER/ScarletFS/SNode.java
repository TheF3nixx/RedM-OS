package ScarletFS;

public abstract class SNode {
    protected String name;
    protected SDirectory parent;
    protected final long createdAt;

    protected SNode(String name, SDirectory parent) {
        this.name = name;
        this.parent = parent;
        this.createdAt = System.currentTimeMillis();
    }

    public String getPath(){
        return parent == null ? "/" + name : parent.getPath() + "/" + name;
    }
}
