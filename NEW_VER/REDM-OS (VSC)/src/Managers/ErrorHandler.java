package Managers;

import IO.IO;
import System.ErrorC;

public class ErrorHandler {

    public static void trigger(ErrorC code, String detail) {
        String message = code.format(detail);
        IO.error(message);
    }

}


