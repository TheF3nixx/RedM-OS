package System;

import java.util.HashMap;

public class EnvVars{
    private static final HashMap<String, String> envVars = new HashMap<>();

    public static void set(String key, String value){
        envVars.put(key.toUpperCase(), value);
    }

    public static String get(String key){
        return envVars.get(key.toUpperCase());
    }

    public static boolean exists(String key){
        return envVars.containsKey(key.toUpperCase());
    }
}
