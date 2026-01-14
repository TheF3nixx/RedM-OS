package Core;
import Exceptions.RedMindException;
import Managers.LogManager;
//Guarda y centraliza todos los posibles errores que te puede dar el programa
public class ErrorHandler {
    
    public static void trigger(RedMindException e){
        trigger(e.getCode());  //Muestra el mensaje "oficial" del código
        System.err.println("DETAILS: " + e.getMessage());  //Muestra detalle adicional de la excepción
        LogManager.log("[ERROR " + e.getCode() + "] → " + e.getMessage());
    }

    public static void trigger(String code){
        switch(code){
            case "RM-0001":
                System.err.println("ERROR: [RM-0001] → File not found.");
                break;
            case "RM-0002":
                System.err.println("ERROR: [RM-0002] → Missing argument for command.");
                break;
            case "RM-0003":
                System.err.println("ERROR: [RM-0003] → Unable to read file.");
                break;
            case "RM-0004":
                System.err.println("ERROR: [RM-0004] → Directory not found.");
                break;
            case "RM-0005":
                System.err.println("ERROR: [RM-0005] → Unrecognized command.");
                break;
            case "RM-0006":
                System.err.println("ERROR: [RM-0006] → Access denied outside of RedMind system.");
                break;
            case "RM-0007":
                System.err.println("ERROR: [RM-0007] → Directory resolution failed.");
                break;
            case "RM-0008": 
                System.err.println("ERROR: [RM-0008] → Unable to determine working directory.");
                break;
            case "RM-0009":
                System.err.println("ERROR: [RM-0009] → File already exists.");
                break;
            case "RM-0010":
                System.err.println("ERROR: [RM-0010] → Could not create file.");
                break;
            case "RM-0011": 
                System.err.println("ERROR: [RM-0011] → Target is a directory.");
                break;
            case "RM-0012":
                System.err.println("ERROR: [RM-0012] → Could not write file.");
                break;
            case "RM-0013":
                System.err.println("ERROR: [RM-0013] → Could not clear system log.");
                break;
            case "RM-0014":
                System.err.println("ERROR: [RM-0014] → Unable to read log.");
                break;
            case "RM-0015":
                System.err.println("ERROR: [RM-0015] → Invalid credentials.");
                break;
            case "RM-0016":
                System.err.println("ERROR: [RM-0016] → Only root users can execute this command");
                break;
            case "RM-0017": 
                System.err.println("ERROR: [RM-0017] → User not found.");
                break;
            case "RM-0018": 
                System.err.println("ERROR: [RM-0018] → Could not login as that user.");
                break;
            case "RM-0019": 
                System.err.println("ERROR: [RM-0019] → You are not in a loginAs session.");
                break;
            case "RM-0020": 
                System.err.println("ERROR: [RM-0020] → You cannot impersonate yourself.");
                break;
            case "RM-0021": 
                System.err.println("ERROR: [RM-0021] → You do not have permission to access this directory.");
                break;
            case "RM-0022": 
                System.err.println("ERROR: [RM-0022] → Could not update line.");
                break;
            case "RM-0023": 
                System.err.println("ERROR: [RM-0023] → Out of bounds.");
                break;
            case "RM-0024": 
                System.err.println("ERROR: [RM-0024] → This file is marked as UNEDITABLE. You cannot modify it.");
                break;
            case "RM-0025": 
                System.err.println("ERROR: [RM-0025] → You can not modify tag lines.");
                break;
            case "RM-0026": 
                System.err.println("ERROR: [RM-0026] → You must passby the user before loginas.");
                break;
            case "RM-0027": 
                System.err.println("ERROR: [RM-0027] → Wrong password. Insert a valid one.");
                break;
            case "RM-0028": 
                System.err.println("ERROR: [RM-0028] → You cannot exit passby while in loginAs mode.");
                break;
            case "RM-0029": 
                System.err.println("ERROR: [RM-0029] → You must be logged in to impersonate another user.");
                break;
            case "RM-0030": 
                System.err.println("ERROR: [RM-0030] → Target user does not exist.");
                break;
            case "RM-0031": 
                System.err.println("ERROR: [RM-0031] → You are already in a passby or loginAs session.");
                break;
            case "RM-0032": 
                System.err.println("ERROR: [RM-0032] → You are not impersonating any user.");
                break;
            case "RM-0033": 
                System.err.println("ERROR: [RM-0033] → You cannot perform this operation on yourself.");
                break;
            case "RM-0034": 
                System.err.println("ERROR: [RM-0034] → Invalid operation: already acting as another user.");
                break;
            case "RM-0035": 
                System.err.println("ERROR: [RM-0035] → You cannot do a passby to another ROOT user.");
                break;
            case "RM-0036": 
                System.err.println("ERROR: [RM-0036] → This group already exists.");
                break;
            case "RM-0037": 
                System.err.println("ERROR: [RM-0037] → Group not found.");
                break;
            case "RM-0038":
                System.err.println("ERROR: [RM-0038] → Either user or group do not exist");
                break;
            case "RM-0039":
                System.err.println("ERROR: [RM-0039] → Unknown subcommand.");
                break;
            case "RM-0040":
                System.err.println("ERROR: [RM-0040] → Could not load groups correctly.");
                break;
            case "RM-0041":
                System.err.println("ERROR: [RM-0041] → Unrecognized boot command. Please insert a valid one.");
                break;
            case "RM-0042":
                System.err.println("ERROR: [RM-0042] → Non-valid criterion. Please user either 'type' or 'name' before putting the value.");
                break;
            case "RM-0043":
                System.err.println("");
                break;
            case "RM-0044":
                System.err.println("");
                break;
            case "RM-0045":
                System.err.println("");
                break;
            case "RM-0046":
                System.err.println("");
                break;
            case "RM-0047":
                System.err.println("");
                break;
            case "RM-0048":
                System.err.println("");
                break;
            case "RM-0049":
                System.err.println("");
                break;
            case "RM-0050":
                System.err.println("");
                break;
            case "RM-0051":
                System.err.println("");
                break;
            case "RM-0052":
                System.err.println("");
                break;
            case "RM-0053":
                System.err.println("");
                break;
            case "RM-0054":
                System.err.println("");
                break;
            case "RM-0055":
                System.err.println("");
                break;
            case "RM-0056":
                System.err.println("");
                break;
            case "RM-0057":
                System.err.println("");
                break;
            case "RM-0058":
                System.err.println("");
                break;
            case "RM-0059":
                System.err.println("");
                break;
            case "RM-0060":
                System.err.println("");
                break;
            default:
                System.err.println("ERROR: [RM-9999] → Unknown internal error.");
        }
    }
    
}
