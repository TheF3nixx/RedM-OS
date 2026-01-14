package traces;

public class Trace{
    private String id;
    private String title; 
    private String desc;
    private TracePattern pattern;
    private String linkedFile;

    public Trace(String id, String title, String desc, TracePattern pattern) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.pattern = pattern;
    }

    public Trace(String id, String title, String desc, TracePattern pattern, String linkedFile) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.pattern = pattern;
        this.linkedFile = linkedFile;
    }
    
    
    public void setLinkedFile(String fileName) {
    this.linkedFile = fileName;
}

    public String getLinkedFile() {
        return this.linkedFile;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public TracePattern getPattern() {
        return pattern;
    }
    
    public void replace(String name){
        this.title = name; 
    }
    
}
