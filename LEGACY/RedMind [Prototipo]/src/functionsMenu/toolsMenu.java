package functionsMenu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import managers.TraceManager;

public class toolsMenu {
    
    public static Scanner sc = new Scanner(System.in);
    public static File currentDirectory = new File(System.getProperty("user.dir")); //Esto capta el directorio actual en el que nos encontramos
    
   public static void crossfadeWithParallelTimeline() {
    ArrayList<String> sim = new ArrayList<>();
    sim.add("v1");sim.add("v2");sim.add("v3");sim.add("v4");sim.add("v5");
    
    ArrayList<String> timeline = new ArrayList<>();
    timeline.add("Alpha");timeline.add("Beta");timeline.add("Gamma");timeline.add("Delta");
    
    System.out.println("--- Crossfade with Parallel Timeline ---");

    System.out.println("Available simulations:");
    for (int i = 0; i < sim.size(); i++) {
        System.out.println((i + 1) + ". " + sim.get(i));
    }
    System.out.print("Select base simulation (number): "); //Comprobar que está entre 1 y 5
    int baseIndex = Integer.parseInt(sc.nextLine()) - 1; //Se resta uno porque si no no coge bien el número

    System.out.print("Select simulation to crossfade with (number): ");
    int targetIndex = Integer.parseInt(sc.nextLine()) - 1;

    System.out.println("Available timelines:");
    for (int i = 0; i < timeline.size(); i++) {
        System.out.println((i + 1) + ". " + timeline.get(i));
    }
    System.out.print("Select timeline (number): ");
    int timelineIndex = Integer.parseInt(sc.nextLine()) - 1;

    System.out.println("\nSimulations selected: " + sim.get(baseIndex) + " + " + sim.get(targetIndex));
    System.out.println("Timeline: " + timeline.get(timelineIndex));

    System.out.print("\nDo you want to proceed? (y/n): ");
    String confirm = sc.nextLine().trim();
    if (!confirm.equalsIgnoreCase("y")) {
        System.out.println("Crossfade aborted.");
        return;
    }

    System.out.println("\n[Crossfading...]");
    for (int i = 0; i <= 20; i++) {
        delay(800);
        System.out.print("▓");
    }
    delay(500);
    System.out.println("\n[Crossfade complete]");
    delay(500);
}

public static void decodeHexadecimalString() {
    System.out.print("Enter hexadecimal string to decode: ");
    String hexInput = sc.nextLine().trim(); //trim() elimina posibles espacios en blanco al inicio y al final para evitar errores durante la conversión

    try { 
        StringBuilder result = new StringBuilder(); // Va construyendo el texto decodificado carácter por carácter
        for (int i = 0; i < hexInput.length(); i += 2) { // Recorre la cadena de dos en dos caracteres. Cada par representa un byte en hexadecimal
            String byteHex = hexInput.substring(i, i + 2); // Extrae dos caracteres seguidos desde la posición i
            int decimal = Integer.parseInt(byteHex, 16); //  Convierte ese byteHex de hexadecimal a decimal en base 16
            result.append((char) decimal); // Convierte ese número decimal a un carácter ASCII (char) y lo añade al resultado
        }
        System.out.println("Decoded output: " + result.toString());
    } catch (Exception e) {
        System.err.println("ERROR: [RM-0017: Failed to decode hexadecimal string]");
    }
}

    public static void accessTracesMenu(){
        System.out.println("Opening traces menu...");
        delay(1000);
        TraceManager.launch();
    }
    
     private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("INTERRUPT EXCEPTION: System instability detected.");
        }
    }
    
}//class
