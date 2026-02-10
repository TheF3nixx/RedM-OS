package ScarletFS;

public record FileRules(
    boolean readable,
    boolean writable,
    boolean deletable,
    boolean visible,
    boolean volatileContent
) {}
