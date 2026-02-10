package ScarletFS;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class SDirectory extends SNode {

    private final Map<String, SNode> children = new TreeMap<>();

    public SDirectory(String name, SDirectory parent) {
        super(name, parent);
    }

    public void add(SNode node) {
        children.put(node.name, node);
    }

    public SNode get(String name) {
        return children.get(name);
    }

    public Collection<SNode> list() {
        return children.values();
    }
}

