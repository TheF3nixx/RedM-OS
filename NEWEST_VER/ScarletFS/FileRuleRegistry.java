package ScarletFS;

import java.util.Map;

public class FileRuleRegistry{
    private static final Map<FileType, FileRules> RULES = Map.of(
        FileType.TXT,  new FileRules(true, true, true, true, false),
        FileType.CONF, new FileRules(true, true, false, true, false),
        FileType.MEM,  new FileRules(false, false, false, false, true),
        FileType.OLD,  new FileRules(true, false, false, true, false),
        FileType.ERX,  new FileRules(false, false, false, false, false),
        FileType.KEY,  new FileRules(false, false, false, false, false)
    );

    public static FileRules rulesFor(FileType type){
        return RULES.get(type);
    }
}
