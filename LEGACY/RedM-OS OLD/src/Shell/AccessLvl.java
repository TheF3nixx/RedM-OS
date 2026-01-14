package Shell;

public enum AccessLvl {
    OPERATOR, ADMINISTRATOR, ROOT, DEVELOPER, ENGINEER;

    public static AccessLvl fromString(String level){
        try {
            return AccessLvl.valueOf(level.toUpperCase());
        } catch (Exception e){
            return OPERATOR;
        }
    }
}
