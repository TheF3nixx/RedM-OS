package traces;

import java.util.Scanner;

public class SignalDesyncTrace extends TracePattern{
    static Scanner sc = new Scanner(System.in);
    public SignalDesyncTrace() {
        super("Signal Desync");
    }
//    
    public static void menu(){
    while(true){
        System.out.println("");
        System.out.println("---TRACE: SIGNAL DESYNC---");
        System.out.println("1. Reconstruct trace");
        System.out.println("2. Show info");
        System.out.println("3. Analyze signal integrity");
        System.out.println("4. Sync with beacon");
        System.out.println("5. Isolate desync frequency");
        System.out.println("0. Return");
        System.out.print("[desync]> ");
        int opc = sc.nextInt();
        switch(opc){
            case 1: break;
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
    

