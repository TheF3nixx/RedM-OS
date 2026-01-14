package traces;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
public class MemoryEchoTrace extends TracePattern{
    static Scanner sc = new Scanner(System.in);
    private static TreeMap<String, Trace> tmTracesMem = new TreeMap<>();

    public MemoryEchoTrace() {
        super("Memory Echo");
    }

    public static void menu(){
    int opc = -1;
    int opc2 = -1;
    loadTraces(tmTracesMem);

    while(opc != 0){
        System.out.println("");
        System.out.println("---MEMORY ECHO---"); //Como tal ya está todo, solo hacen falta las validaciones/errores
        System.out.println("1. View loaded M.E Traces"); 
        System.out.println("2. Manage M.E Traces");
        System.out.println("3. Load external echo"); 
        System.out.println("4. Load internal echo"); 
        System.out.println("0. Return");
        System.out.print("[memTrace]> ");
        opc = Integer.parseInt(sc.nextLine()); 

        switch(opc){
            case 1:
                System.out.println("");
                System.out.println("Loaded Memory Echo Traces:");
                for(String id : tmTracesMem.keySet()){
                    Trace t = tmTracesMem.get(id);
                    if(t.getLinkedFile() == null){
                        System.out.println("[" + id + "] --- " + t.getTitle());
                    }else{
                        System.out.println("[" + id + "] --- " + t.getTitle() + " --- " + t.getLinkedFile());
                    }
                       
                }
                break;

            case 2:
                while(opc2 != 0){
                    System.out.println("");
                    System.out.println("---MANAGEMENT: MEMORY ECHO---");
                    System.out.println("1. Add trace");
                    System.out.println("2. Delete trace");
                    System.out.println("3. Edit trace"); 
                    System.out.println("4. Link to file (.mem) "); 
                    System.out.println("0. Return");
                    System.out.print("[management]> ");
                    opc2 = Integer.parseInt(sc.nextLine());

                    switch(opc2){
                        case 1: 
                            addNewTrace();
                            break;
                        case 2: 
                            deleteTrace();
                            break;
                        case 3: 
                            editTrace(); 
                            break;
                        case 4: 
                            linkToFile();
                            break;
                        case 0:
                            break;
                        default:
                            System.err.println("ERROR: [RM-0012: Invalid menu option]");
                    }
                }
                break; 

            case 3:
                System.out.println("");
                System.out.println("Available files:");
                listMem(); //Mostrar los archivos .mem para que el usuario elija cual abrir
                System.out.println("");
                System.out.print("Insert file name to view: ");
                String file = sc.nextLine().trim();
                try {
                    System.out.println("");
                    System.out.println("------------------------------------------------------------------------------------------------");
                    loadEcho(file);
                    System.out.println("------------------------------------------------------------------------------------------------");
                } catch (FileNotFoundException ex) {
                    System.err.println("ERROR: [RM-0011: File not found or is a directory]");
                }
                break;

            case 4:
                loadInternalEcho();
                break;

            case 0:
                System.out.println("Returning...");
                break;

            default:
                System.err.println("ERROR: [RM-0012: Invalid menu option]");
        }
    }
}
    
    public static void loadTraces(TreeMap<String, Trace> tmTracesMem){
        tmTracesMem.put("RMT-932", new Trace("RMT-932", "Subject Alpha", "Recovers corrupted mindsets from Subject Alpha.", new MemoryEchoTrace()));
        tmTracesMem.put("RMT-941", new Trace("RMT-941", "Project Lazarus", "Extracts memories from deceased agents.", new MemoryEchoTrace()));
        tmTracesMem.put("RMT-970", new Trace("RMT-970", "Z-Series Mindprint", "Imperfect echo from prototype neural net Z1.", new MemoryEchoTrace()));
    }
    
     public static void loadEcho(String fileName) throws FileNotFoundException{
         String ECHO_ROUTE = "classified/echoes";
         File memFile = new File(ECHO_ROUTE, fileName);
         
         try (BufferedReader br = new BufferedReader(new FileReader(memFile))){
            String firstLine = br.readLine(); 
            System.out.println(firstLine);
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
         }catch(IOException ex){
            System.err.println("ERROR: [RM-0006: Error reading the file]");
         }
         
     }
     
     public static void listMem(){
          String ECHO_ROUTE = "classified/echoes";
          File dir = new File(ECHO_ROUTE);
          String[] files = dir.list();
          for(String fileName : files){
              System.out.println(fileName);
          }
     }
     
     public static void addNewTrace(){
         System.out.println("");
         System.out.println("[add]> Enter trace id: ");
         String id = sc.nextLine().trim();
         
         if(tmTracesMem.containsKey(id)){//Comprobamos que no existe antes de continuar
               System.err.println("ERROR: [RM-0033: Trace id already exists]");
               return;
         } 
         
         System.out.println("[add]> Enter title for trace " + id + ":");
         String title = sc.nextLine().trim();
         System.out.println("[add]> Enter description: ");
         String desc = sc.nextLine().trim();
         
         tmTracesMem.put(id, new Trace(id, title, desc, new MemoryEchoTrace()));//Investigar cómo hacer que se escriba en el treemap global
         System.out.println("[SYSTEM]> Trace successfully added.");
     }
     
      public static void deleteTrace(){
          System.out.println("");
         System.out.println("Trace list: ");
         for(Trace t : tmTracesMem.values()){
             System.out.println(t.getId() + " ---------- " + t.getDesc());
         }
         
         System.out.println("[delete]> Select trace ID to delete: ");
         String delId = sc.nextLine().trim();
         
         if(tmTracesMem.containsKey(delId)){
             tmTracesMem.remove(delId);
             System.out.println("[SYSTEM]> Trace deleted.");
         }else{
             System.err.println("ERROR: [RM-0034: Could not find trace]");
         }
         
     }
      
     public static void editTrace(){
         System.out.println("");
         System.out.println("Trace list: ");
         for(Trace t : tmTracesMem.values()){
             System.out.println(t.getId() + " ---------- " + t.getDesc());
         }
         
         System.out.println("[edit]> Select trace ID to edit: ");
         String edId = sc.nextLine().trim();
         
         if(tmTracesMem.containsKey(edId)){
             int opc;
             do{
                System.out.println("--- EDIT TRACE " + edId + " ---");
                System.out.println("1. Id");
                System.out.println("2. Name");
                System.out.println("3. Description");
                System.out.println("0. Return");
                System.out.println("[edit]> ");
                opc = Integer.parseInt(sc.nextLine()); 
                switch(opc){
                    case 1: 
                        System.out.println("Insert new Id for trace: ");
                        String newId = sc.nextLine().trim();
                        tmTracesMem.get(edId).replace(newId);
                        break;
                    case 2: 
                        System.out.println("Insert new name for trace: ");
                        String newName = sc.nextLine().trim();
                        tmTracesMem.get(edId).replace(newName);
                        break;
                    case 3: 
                        System.out.println("Insert new description for trace: ");
                        String newDesc = sc.nextLine().trim();
                        tmTracesMem.get(edId).replace(newDesc);
                        break;
                    case 0: break;
                }
             }while(opc != 0);
             
         }else{
             System.err.println("ERROR: [RM-0034: Could not find trace]");
         }
         
     }
     
      public static void linkToFile() {
          //1. Pedimos el nombre del archivo
          //2. Indicamos la ruta donde se guardará, junto a su extensión
          //3. Comprobar si existe el archivo y si no, crear uno nuevo
          //4. Guardar la ruta dentro del objeto Trace
    // Mostrar las trazas disponibles
    System.out.println("Trace list:");
    for (Trace t : tmTracesMem.values()) {
        System.out.println(t.getId() + " ---------- " + t.getDesc());
    }

    // Seleccionar ID
    System.out.print("[link]> Select trace ID: ");
    String linkId = sc.nextLine().trim();

    // Validar existencia
    if (!tmTracesMem.containsKey(linkId)) {
        System.err.println("ERROR: [RM-0034: Could not find trace]");
        return;
    }

    // Mostrar los archivos .mem disponibles
    listMem();  // Solo si el ID es correcto

    // Seleccionar archivo
    System.out.print("[link]> Enter filename to link (no extension needed): ");
    String fileName = sc.nextLine().trim();
    File file = new File("classified/echoes", fileName + ".mem");

    // Preguntar si se quiere crear si no existe
    if (!file.exists()) {
        System.out.print("[SYSTEM]> File does not exist. Create it now? (y/n): ");
        String opt = sc.nextLine().trim().toLowerCase();

        if (opt.equals("y")) {
            try {
                file.createNewFile();
                System.out.println("File created: " + file.getName());
            } catch (IOException ex) {
                System.err.println("ERROR: [RM-0016: Could not create file]");
                return;
            }
        } else {
            System.out.println("[SYSTEM]> Linking cancelled.");
            return;
        }
    }

    // Vinculación
    Trace tr = tmTracesMem.get(linkId);
    tr.setLinkedFile(file.getName());
    System.out.println("Trace " + linkId + " successfully linked to " + file.getName());
}
      
      public static void loadInternalEcho(){
          //1. Pedimos el ID del Trace
          System.out.println("Enter Trace ID to load its linked echo : ");
          String id = sc.nextLine().trim();
          //2. Buscamos a ver si existe
          if(!tmTracesMem.containsKey(id)){
              System.err.println("ERROR: [RM-0034: Could not find trace]");
              return;
          }
          //3. Inicializamos variables de un objeto Trace con el id que le pasamos y otra para buscar el archivo asociado a dicho trace
          Trace t = tmTracesMem.get(id);
          String fileName = t.getLinkedFile();
          //4. Comprobamos si está unido o no
          if(fileName == null){//Si no detecta nada con el getLinkedFile, es porque no tiene nada asociado
              System.out.println("ERROR: [RM-0035: This trace is not linked to any file]");
              return;
          } 
          //5. Si está unido, tenemos que "encontrar" a cuál está unido para comprobar que existe igualmente
          File file = new File("classified/echoes", fileName);//Creamos un archivo temporal para encontrar el archivo
          if(!file.exists()){
              System.out.println("ERROR: [RM-0036: File does not exist]");
              return;
          }
          //6. Por último, leemos el archivo con un formato ligeramente distinto (cambiar)
          System.out.println("===== INTERNAL ECHO --- " + fileName + " =====");
          try (BufferedReader br = new BufferedReader(new FileReader(file))){
              String line; 
              while((line = br.readLine()) != null){
                  System.out.println(line);
              }
              
          } catch (Exception e) {
              System.err.println("ERROR: [RM-0006: Error reading the file]");
          }
          
          
      }
      
      
      
      

     
}
         
         
         
     
    
    

