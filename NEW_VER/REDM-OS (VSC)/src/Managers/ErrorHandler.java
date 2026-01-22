package Managers;

import IO.IO;

public class ErrorHandler {
    
    public static void trigger(String code, String detail){
        switch(code){
            case "001":
                IO.error("[ERROR: RM-001]> Insert a valid number");
                break;
            case "002":
                IO.error("[ERROR: RM-002]> Out of bounds " + detail);
                break;
            case "003"://Para archivos en general
                IO.error("[ERROR: RM-003]> Could not find archive: " + detail);
                break;
            case "004":
                IO.error("[ERROR: RM-004]> File '" + detail + "' is a directory");
                break;
            case "005"://Específico para archivos de tipo File
                IO.error("[ERROR: RM-005]> Could not find file: " + detail);
                break;
            case "006":
                IO.error("[ERROR: RM-006]> Couldn't read: " + detail);
                break;
            case "007":
                IO.error("[ERROR: RM-007]> Invalid boot mode.");
                break;
            case "008":
                IO.error("[ERROR: RM-008]> Error accessing directory: " + detail);
                break;
            case "009":
                IO.error("[ERROR: RM-009]> Cannot access directory: " + detail);
                break;
            case "010":
                IO.error("[ERROR: RM-010]> Failed to create directory: " + detail);
                break;
            case "011":
                IO.error("[ERROR: RM-011]> Directory already exists: " + detail);
                break;
            case "012":
                IO.error("[ERROR: RM-012]> Directory does not exist: " + detail);
                break;
            case "013":
                IO.error("[ERROR: RM-013]> Failed to delete directory: " + detail);
                break;
            case "014":
                IO.error("[ERROR: RM-014]> Invalid name or extension for file.");
                break;
            case "015":
                IO.error("[ERROR: RM-015]> File already exists: " + detail);
                break;
            case "016":
                IO.error("[ERROR: RM-016]> Failed to create file: " + detail);
                break;
            case "017":
                IO.error("[ERROR: RM-017]> Failed to access file: " + detail);
                break;
            case "018":
                IO.error("[ERROR: RM-018]> Failed to delete file: " + detail);
                break;
            case "019":
                IO.error("[ERROR: RM-019]> File does not exist: " + detail);
                break;
            case "020":
                IO.error("[ERROR: RM-020]> Source file not valid: " + detail);
                break;
            case "021":
                IO.error("[ERROR: RM-021]> Error while copying file: " + detail);
                break;
            case "022":
                IO.error("[ERROR: RM-022]> Destiny must be an existent directory.");
                break;
            case "023":
                IO.error("[ERROR: RM-023]> Could not move file to: " + detail);
                break;
            case "024":
                IO.error("[ERROR: RM-024]> Unknown command: " + detail);
                break;
            case "025":
                IO.error("[ERROR: RM-025]> Missing argument(s).");
                break;
            case "026":
                IO.error("[ERROR: RM-026]> State must be ON or OFF");
                break;
            case "027":
                IO.error("[ERROR: RM-027]> You cannot do that in this precise moment, pal.");
                break;
            case "028":
                IO.error("[ERROR: RM-028]> Category does not exist: " + detail);
                break;
            case "029":
                IO.error("[ERROR: RM-029]> Capability does not exist: " + detail);
                break;
            case "030":
                break;
            case "031":
                break;
            case "032":
                break;
            case "033":
                break;
            case "034":
                break;
            case "035":
                break;
            case "036":
                break;
            case "037":
                break;
            case "038":
                break;
        }
    }
}
