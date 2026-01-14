package Managers;

import Core.ErrorHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;



public class TagManager {
    /*
    Esta es una clase que detecta y almacena etiquetas presentes en un archivo,
    permite consultar si una etiqueta está activa y centraliza la lógica para 
    evitar repetir código por todo el sistema.
    
    Es como un detector de banderas ocultas en el archivo, y 
    avisa al sistema de qué puede o no puede hacer
    */
    
    private final static HashSet<String> detectedTags = new HashSet<>();
    private static String keyId = null;//Para guardar "X" si hay #KEY=X
    private static int MAX = 10;
    public static boolean isUneditable = false;
    public static boolean isProtected = false;
    
    public static void searchTags(File file){
       detectedTags.clear();//Inicializamos el Set para almacenar las tags
       keyId = null;//También inicializamos la "llave", por si hay archivos protegidos
       //Debemos leer el archivo y buscar lo que coincida con lo que queremos
       try(BufferedReader br = new BufferedReader(new FileReader(file))){
           String line;
           int count = 0; 
           while((line = br.readLine()) != null && count < MAX){
               if(line.startsWith("#")){//Si empieza por #, leemos lo que hay después
                   line = line.trim();
                   detectedTags.add(line);
                   if(line.startsWith("#KEY=")){
                       keyId = line.substring(5).trim();
                   }
               }
               count++;
           }
       }catch(Exception rme){
           ErrorHandler.trigger("RM-0003");
       }
    }
    
    public static boolean isUneditable(){
        return detectedTags.contains("#UNEDITABLE");
    }
    
    public static boolean isProtected() {
        return detectedTags.contains("#PROTECTED");
    }
    
    public static String getKeyId() {
        return keyId;
    }
    
    public static HashSet<String> getAllTags(){
        return new HashSet<>(detectedTags);
    }
    
    
    
    
    
    
    
    
    
}