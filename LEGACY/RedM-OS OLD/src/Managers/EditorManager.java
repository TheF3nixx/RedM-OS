package Managers;

import Core.ErrorHandler;
import Exceptions.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class EditorManager {
    
    private static boolean editing = true;
    private static Scanner sc = new Scanner(System.in);
    private static final Map<String, Runnable> sec = new HashMap<>();//Simple editor commands
    private static final Map<String, Consumer<String[]>> cec = new HashMap<>();//Complex editor commands
    private static String[] currentInputParts;
    private static ArrayList<String> buffer = new ArrayList<>();
    private static File currentFile;

    
    public static void initiateEditor(String fileToEdit){
        editing = true;
        currentFile = new File(CommandManager.currentDirectory, fileToEdit);
        initComms();//Inicializamos los comandos
        
        //Cargamos el contenido del archivo en un buffer
        buffer.clear();
        if(currentFile.exists()){//Comprobamos si el archivo existe
            try(Scanner r = new Scanner(currentFile)){//Cargamos línea a línea el contenido en un buffer
                while(r.hasNextLine()){
                    buffer.add(r.nextLine());
                }
            }catch (Exception e){
                ErrorHandler.trigger("RM-0003");
            }
        }

        System.out.println("[EDITOR]> Editing file: " + fileToEdit);
        System.out.println("Type ':h' to see available editor commands.");
        System.out.println("");
        
        while(editing){
            System.out.print("[EDITOR]> ");
            String comm = sc.nextLine().trim();
            
            if(comm.equalsIgnoreCase(":q")){
                 try {//Guardamos el archivo
                    Files.write(currentFile.toPath(), buffer, StandardCharsets.UTF_8);
                    System.out.println("[EDITOR]> File saved. Exiting editor...");
                } catch (IOException ex) {
                    System.out.println("[EDITOR]> Could not save file before exiting.");
                }
                editing = false;
                return;
            }
            executeCommand(comm);
            
        }
        
    }
    
    private static void initComms(){
        sec.put(":h", () -> {
            System.out.println("---AVAILABLE EDITOR COMMANDS---");
            System.out.println(":w [line] [content] .......................... Overwrites the content of a line");
            System.out.println(":d [line] .................................... Deletes a complete line of the file");
            System.out.println(":s ........................................... Saves the content, remains in editor");
            System.out.println(":q ........................................... Saves the content and exits the editor");
            System.out.println(":a [text] .................................... Appends a new line to the end");
            System.out.println(":x ........................................... Exits without saving");
            System.out.println(":i [line] [text] ............................. Inserts a new line at position (shifts)");
            System.out.println(":head ........................................ Shows first 10 lines");
            System.out.println(":tail ........................................ Shows last 10 lines");
        });
//--------------------------------------------------------------------------------------------------------------------//        
        cec.put(":w", args -> {
            if(args.length < 3){
                System.out.println("Usage: :w [line] [content]");
                return;
            }
            
            try{
                int line = Integer.parseInt(args[1]) - 1;//Las líneas empiezan en 1, nunca en 0
                String newText = builder(args);//Combina lo que venga después del número
                
                if (buffer.size() > line && buffer.get(line).startsWith("#")){//Con esto no se permite modificar líneas tipo etiqueta
                    throw new CannotEditTags(newText);
                }

                while(buffer.size() <= line){//Expande el buffer si la línea aún no existe
                    buffer.add("");
                }
                
                buffer.set(line, newText);
                System.out.println("[EDITOR]> Line " + (line + 1) + " updated.");

            }catch (NumberFormatException e){
                ErrorHandler.trigger("RM-0022");
            }catch(RedMindException rme){
                ErrorHandler.trigger(rme);
            }
            
        });
//--------------------------------------------------------------------------------------------------------------------//        
        cec.put(":d", args -> {
            if(args.length < 2){
                System.out.println("Usage: :d [line]");
                return;
            }

            try{
                int lineToDel = Integer.parseInt(args[1]) - 1;

                if(lineToDel < 0 || lineToDel >= buffer.size()){
                    throw new OutOfBoundsException(args[1]);
                }

                String removedLine = buffer.remove(lineToDel);
                System.out.println("[EDITOR]> Line " + (lineToDel + 1) + " deleted: \"" + removedLine + "\"");

            } catch(NumberFormatException e){
                ErrorHandler.trigger("RM-0022"); // Línea no válida
            } catch(RedMindException rme){
                ErrorHandler.trigger(rme);
            }
        });

//--------------------------------------------------------------------------------------------------------------------//       
        sec.put(":s", () -> {
             try{
                Files.write(currentFile.toPath(), buffer, StandardCharsets.UTF_8);
                System.out.println("[EDITOR]> File saved.");
            }catch (IOException ex){
                System.out.println("[EDITOR]> Could not save file.");
            }
        });
//--------------------------------------------------------------------------------------------------------------------//     
        cec.put(":a", args -> {
            if (args.length < 2) {
                System.out.println("Usage: :a [text]");
                return;
            }

            String newLine = builder(args); // Desde args[1] hasta el final
            buffer.add(newLine);
            System.out.println("[EDITOR]> Appended line: \"" + newLine + "\"");
        });

//--------------------------------------------------------------------------------------------------------------------//     
        sec.put(":x", () -> {
            editing = false;
            System.out.println("[EDITOR]> Exiting without saving.");
        });

//--------------------------------------------------------------------------------------------------------------------//        
        cec.put(":i", args -> {
            if (args.length < 3) {
                System.out.println("Usage: :i [line] [text]");
                return;
            }

            try {
                int line = Integer.parseInt(args[1]) - 1;
                String text = builder(args);

                if (line < 0 || line > buffer.size()) {
                    throw new OutOfBoundsException(args[1]);
                }

                buffer.add(line, text);
                System.out.println("[EDITOR]> Inserted line at " + (line + 1) + ": \"" + text + "\"");
            } catch (NumberFormatException e) {
                ErrorHandler.trigger("RM-0022");
            } catch (RedMindException ex) {
                ErrorHandler.trigger(ex);
            }
        });

//--------------------------------------------------------------------------------------------------------------------//     
        sec.put(":head", () -> {
            int maxLines = Math.min(10, buffer.size());
            if (maxLines == 0) {
                System.out.println("[EDITOR]> File is empty.");
                return;
            }

            for (int i = 0; i < maxLines; i++) {
                System.out.println((i + 1) + " | " + buffer.get(i));
            }
        });

//--------------------------------------------------------------------------------------------------------------------//
        sec.put(":tail", () -> {
            int total = buffer.size();
            if (total == 0) {
                System.out.println("[EDITOR]> File is empty.");
                return;
            }

            int start = Math.max(0, total - 10);
            for (int i = start; i < total; i++) {
                System.out.println((i + 1) + " | " + buffer.get(i));
            }
        });

        
    }
//--------------------------------------------------------------------------------------------------------------------//
    public static void executeCommand(String input){
        currentInputParts = input.trim().split(" ");//Divide el input en partes por el espacio
        String command = currentInputParts[0];//Marca como el primero el comando en sí, el resto son parámetros
        
        if(sec.containsKey(command)){
            sec.get(command).run();
        }else if(cec.containsKey(command)){
            cec.get(command).accept(currentInputParts);
        }else{
            ErrorHandler.trigger("RM-0005");
        }
    } 
//--------------------------------------------------------------------------------------------------------------------//
    private static String builder(String[] args){
        StringBuilder sb = new StringBuilder();
        for(int i = 2; i < args.length; i++){
            sb.append(args[i]);
            if(i < args.length - 1){
                sb.append(" ");
            }
        }
        return sb.toString();
    }
    
//--------------------------------------------------------------------------------------------------------------------//

}
