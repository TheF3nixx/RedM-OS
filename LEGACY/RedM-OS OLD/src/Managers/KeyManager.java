package Managers;

import Core.ErrorHandler;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class KeyManager {
    private static final String KEYS_FILE = "keys/keys.txt";
    private static final HashMap<String, String> keyMap = new HashMap<>();
    
    public static void loadKeys(){
        keyMap.clear();
        
        File f = new File(KEYS_FILE);
        if(!f.exists()){
            ErrorHandler.trigger("RM-0001");
            return;
        }
        
        try(BufferedReader br = new BufferedReader(new FileReader(KEYS_FILE))){
            String line;
            while((line = br.readLine()) != null){
                if(line.contains("=")){
                    String[] parts = line.split("=");
                    if(parts.length == 2){
                        keyMap.put(parts[0].trim(), parts[1].trim());
                    }
                }
            }
        }catch(Exception e){
            ErrorHandler.trigger("RM-0003");
        }
    }
    
    public static boolean validateKey(String keyId, String input){
        return keyMap.containsKey(keyId) && keyMap.get(keyId).equals(input);
    }
    
    public static String getExpectedKey(String keyId) {
        return keyMap.get(keyId);
    }
    
    public static boolean unlock(String keyId){
        Scanner sc = new Scanner(System.in);
        System.out.print("[UNLOCK]> Enter the password for this file: ");
        String input = sc.nextLine().trim();
        
        if(validateKey(keyId, input)){
            System.out.println("\u001B[32m[FILE UNLOCKED]\u001B[0m");
            Shell.Shell.delay(1000);
            return true;
        }else{
            System.out.println("\u001B[31m[ACCESS DENIED]\u001B[0m");
            return false;
        }
        
    }
    
    
    
    
    
    
}
