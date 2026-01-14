package managers;

import java.util.Scanner;
import java.util.TreeMap;
import traces.DecisionForkTrace;
import traces.MemoryEchoTrace;
import traces.Trace;
import traces.SignalDesyncTrace;

public class TraceManager {
    static Scanner sc = new Scanner(System.in);
    private static TreeMap<String, Trace> tmTraces = new TreeMap<>();
    public static void launch(){
        loadTraces(tmTraces); //Cargamos en la variable del treemap 
        delay(1000);
        System.out.println("[SYSTEM]> Traces loaded correctly.");
        delay(1000);
        while(true){
            System.out.println("");
            System.out.println("---TRACE MENU---");
            System.out.println("1. Memory Echo"); //wip
            System.out.println("2. Decision Fork"); //ya lo haré
            System.out.println("3. Signal Desync"); //ya lo haré
            System.out.println("4. View loaded traces"); //done
            System.out.println("0. Return");
            System.out.print("[trace]> ");
            String input = sc.nextLine().trim();
            switch(input){
                case "1": MemoryEchoTrace.menu(); break;
                case "2": DecisionForkTrace.menu(); break;
                case "3": SignalDesyncTrace.menu(); break;
                case "4": 
                        System.out.println("");
                        System.out.println("Loaded traces:");
                        for(String id : tmTraces.keySet()){//Mostramos los pares id-nombre
                                Trace t = tmTraces.get(id);
                                System.out.println("[" + id + "] --- " + t.getTitle() + " --- " + t.getPattern());
                        }
                    break;
                case "0": return; 
            }
        }
        
    }
    
    public static void loadTraces(TreeMap<String, Trace> tmTraces){
        tmTraces.put("RMT-932", new Trace("RMT-932", "Subject Alpha", "Recovers corrupted mindsets from Subject Alpha.", new MemoryEchoTrace()));
        tmTraces.put("RMT-941", new Trace("RMT-941", "Project Lazarus", "Extracts memories from deceased agents.", new MemoryEchoTrace()));
        tmTraces.put("RMT-950", new Trace("RMT-950", "Test A", "Simulates reality where subject chose not to deploy RedMind.", new DecisionForkTrace()));
        tmTraces.put("RMT-951", new Trace("RMT-951", "Operation Split", "Analyzes consequences of the abandoned fork timeline.", new DecisionForkTrace()));
        tmTraces.put("RMT-960", new Trace("RMT-960", "Sector Δ", "Desynchronization detected in Delta sector beacon.", new SignalDesyncTrace()));
        tmTraces.put("RMT-961", new Trace("RMT-961", "Bio-Feed", "Erratic brainwave signals from experimental soldier.", new SignalDesyncTrace()));
        tmTraces.put("RMT-970", new Trace("RMT-970", "Z-Series Mindprint", "Imperfect echo from prototype neural net Z1.", new MemoryEchoTrace()));
}
    
    
    

    
    
    /*
    NO LO HAGO ASÍ PORQUE SALE EN ESPAÑOL Y ESTO ES EN INGLÉS, PERO ASÍ ES COMO SE HACE UN 
    MENÚ CARGANDO LAS OPCIONES ANTES
    
    ArrayList<Object> optMenu = new ArrayList<>();
        loadMenuOpt(optMenu);
        
        do{       
            opt = pintaMenu(optMenu);
            switch(opt){
                case 1: break;
                case 2: 
                    do{
                        int opt2 = pintaMenu(optMenu2);
                        switch(opt2){
                            case 1: break;
                            [...]
                    }
                 }while(opt2 != 0);
                break;
                case 3: break;
                case 0: break;
                default: System.err.println("ERROR: [RM-0012: Invalid menu option]");;
            }
            
        }while (opt != 0);
        
    }
    
    private static void loadMenuOpt(ArrayList<Object> optMenu){
     ArrayList<Object> optMenu2 = new ArrayList<>();
    
        optMenu2.add(""); [...]
    
        optMenu.add("TRACE MENU");
        optMenu.add("Memory Echo");
        optMenu.add("Decision Fork");
        optMenu.add("Signal Desync");
    
        METEMOS PRIMERO LAS OPCIONES DE LOS SUBMENÚS PARA QUE NO 'PETE'
    }
    */

    private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("INTERRUPT EXCEPTION: System instability detected.");
        }
    }
    
    
}
