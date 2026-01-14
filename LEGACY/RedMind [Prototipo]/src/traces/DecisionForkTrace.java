package traces;

import java.util.Scanner;

public class DecisionForkTrace extends TracePattern{
    static Scanner sc = new Scanner(System.in);
    public DecisionForkTrace() {
        super("Decision Fork");
    }
    
     public static void menu(){
         while(true){
        System.out.println("");
        System.out.println("---TRACE: DECISION FORK---");
        System.out.println("1. Reconstruct trace");
        System.out.println("2. Show info");
        System.out.println("3. Visualize alternate timeline");
        System.out.println("4. Simulate outcome divergence");
        System.out.println("5. Export fork data");
        System.out.println("0. Return");
        System.out.print("[fork]> ");
        int opc = sc.nextInt();
        switch(opc){
            case 1:  break;
            case 2: break;
            case 3: break;
            case 4: break;
            case 5: break;
            case 0: return; 
            default: System.out.println("Invalid option."); break;
        }
    }
     
     }
    
    
    
    
}
