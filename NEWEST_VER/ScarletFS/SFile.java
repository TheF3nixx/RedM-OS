package ScarletFS;

public class SFile extends SNode{
    private final FileType type;
    private String content;

    public SFile(String name, FileType type, SDirectory parent) {
        super(name, parent);
        this.type = type;
    }

    public FileType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

}
