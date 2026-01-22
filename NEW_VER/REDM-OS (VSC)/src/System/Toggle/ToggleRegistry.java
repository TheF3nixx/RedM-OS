package System.Toggle;

import java.util.HashMap;
import java.util.Map;

public class ToggleRegistry {
    private static final Map<String, Boolean> toggles = new HashMap<>();

    public static void register(String key, boolean defaultState){
        toggles.putIfAbsent(key, defaultState);
    }

    public static boolean exists(String key){
        return toggles.containsKey(key);
    }

    public static void set(String key, boolean state){
        toggles.put(key, state);
    }

    public static boolean get(String key){
        return toggles.getOrDefault(key, false);
    }

    public static Map<String, Boolean> all() {
        return Map.copyOf(toggles);
    }

}
