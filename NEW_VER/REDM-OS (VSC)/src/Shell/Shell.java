package Shell;

import IO.IO;
import Managers.CommandManager;
import Managers.ErrorHandler;
import System.*;

public class Shell{
    public static void main(String[] args){
        //Activa todas las capacidades del sistema al iniciar
        SystemState.enableAll();
        //La pantalla de booteo
        IO.output("[RedMind Process Terminal]");
        IO.output("Please, insert a valid boot command to enter the system.");
        while(!SystemState.isBooted()){
            String mode = IO.input("> ");
            switch(mode){
                case "boot -redmos --recoverymode":
                    BootLoader.boot(BootMode.RECOVERY);
                    SystemState.setBooted(true);
                    break;
                case "boot -redmos --adminmode"://ya activa los subsistemas por defecto
                    BootLoader.boot(BootMode.ADMIN);
                    SystemState.setBooted(true);
                    break;
                case "boot -redmos":
                    BootLoader.boot(BootMode.NORMAL);
                    SystemState.setBooted(true);
                    break;
                case "boot -redmos --safemode":
                    BootLoader.boot(BootMode.SAFE);
                    SystemState.setBooted(true);
                    break;
                case "boot -redmos --emergencymode":
                    BootLoader.boot(BootMode.EMERGENCY);
                    SystemState.setBooted(true);
                    break;
                case "boot -subsystems --all":
                    BootLoader.boot(BootMode.SUBSYSTEMS);//terminar
                    SystemState.setBooted(false);
                    break;
                case "f":
                    SystemState.setBooted(true);
                    break;
                default:
                    ErrorHandler.trigger(ErrorC.INVALID_BOOT_MODE, null);
            }    
        }
        //Esta es la interfaz de la terminal en sí
        CommandManager.init();
        System.out.println("\u001B[31m[REDMIND TERMINAL ||\u001B[0m V0.5\u001B[31m]\u001B[0m");
        System.out.println("Tip: Write '-help' to view available commands.");

        while(true){
            String inputCommand = IO.input("> ");
            CommandManager.execute(inputCommand);
        }   
        
    }

    /* 
    private static void delay(long ms){
        try{Thread.sleep(ms);}catch(InterruptedException e){}
    }
    */
}

        /* ASI ES COMO FUNCIONA EL CRASHEO DE LA APP Y SU CONSECUENTE RECOVERY
            try{
                accion
            }catch(Exception e){
                SystemCrash.fatal(e, "mensaje");
            }
        */

        /* ASÍ ES COMO SE ACTIVAN LOS SUBSISTEMAS (HAY QUE CREAR UN OBJETO)
            Scheduler sch = new Scheduler(); --> Subsistema que queremos activar
            SubsystemManager.register(sch);
            SubsystemManager.register(new Security());

            SubsystemManager.start("Scheduler");
            SubsystemManager.start("Security");

            SubsystemManager.list();
        */

        /* ASÍ ES COMO EL SISTEMA CUENTA TICKS
        SystemClock.pulse(); //CADA pulse() ES UN TICK QUE CUENTA +1
        SystemClock.pulse();
        SystemClock.pulse();
        SystemClock.pulse();
        SystemClock.pulse();
        SystemClock.pulse();

       Scheduler s = (Scheduler) SubsystemManager.get("Scheduler");
       IO.output("Ticks: " + s.getTicks());
        */
