package Managers;

import IO.*;
//Clase auxiliar que ayuda a centralizar todos los errores de la aplicación
public class ErrorHandler{
    public static void trigger(String code, String detail){
        switch(code){
            case "001":
                IO.error("001", "Command not recognized: " + detail);
                break;
            case "002":
                IO.error("002", "Missing argument. " + detail);
                break;
            case "003":
                IO.error("003", detail);
                break;
            case "004":
                IO.error("004", detail);
                break;
            case "005":
                IO.error("005", detail);
                break;
        }
    }
}
