package Shell;

//Jars a importar
import IO.*;
import Security.*;
import Sys.*;

//Paquetes a importar
import Managers.*;

public class Shell{
    public static String user;
    public static String password;
    
    public static void main(String[] args){
        //Iniciamos todos los paquetes para asegurar el correcto funcionamiento
        Security.initSystemKey();
        IO.output("Session skey --> " + Security.getSystemKey());
        CommandManager.initialize();
        IO.output("-----------------------------------------------------");
        delay(800);
        Sys.initiateKernel();
        IO.output("-----------------------------------------------------");
        Sys.notify("To use the terminal, select first the boot type:");
        boolean isBooted = false;
        while(!isBooted){
            IO.output("1. Normal boot");
            IO.output("2. Diagnostic boot");
            String bootType = IO.input("Select: ");
            
            switch(bootType){
                case "1":
                    BootModule.bootNormal();
                    isBooted = true;
                    break;
                case "2":
                    BootModule.bootDiagnostic();
                    isBooted = true;
                    break;
                case "f":
                    isBooted = true;
                    break;
                default:
                    IO.output("Please select an existent boot mode.");
            }
        }
        
        //Marcamos la versión
        Sys.setVersion("V1.0.0");
        IO.output("To use the terminal, you must instroduce your username and your password. If you don't have one yet, please contact an Administrator.");
        boolean inSys = false;
        while(!inSys){
            user = IO.input("[USERNAME]> ");
            password = IO.input("[PASSWORD]> ");
            if(Security.authenticate("control/users.rmu", user, password)){
                IO.output("\u001B[32m[ACCESS GRANTED]\u001B[0m");
                inSys = true;
                delay(1000);
                Log.write("Entered the system.", user);
            }
        }
        
        IO.output("[REDMIND TERMINAL | " + Sys.getVersion() + "]");
        
        while(true){
            String comm = IO.input("> ");
            CommandManager.execute(comm);
        }
                
    }
    
    public static void delay(int milliseconds) {
        try{
            Thread.sleep(milliseconds);
        }catch (InterruptedException e){
            //Si el hilo es interrumpido, restauramos el estado de interrupción
            Thread.currentThread().interrupt();
        }
    }
}
