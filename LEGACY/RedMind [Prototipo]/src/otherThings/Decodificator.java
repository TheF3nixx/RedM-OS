package otherThings;

public class Decodificator {
    /*
    Esta es la clase Decodificador. Esta lo que hace es que toma una cadena de texto
    línea por línea en una base (hexadecimal, octal...) y la va desglosando. Así de 
    "simple". Falta terminarlo e implementarlo bien.
    */
   
    
    
    public static String decode(String encoded, String base) {
        switch (base.toLowerCase()) {
            case "binary":
                return decodeBinary(encoded);
            case "hex":
            case "hexadecimal":
                return decodeHex(encoded);
            case "base64":
                return decodeBase64(encoded);
            case "ascii":
                return decodeASCII(encoded);
            default:
                return "[ERROR] Unsupported base: " + base;
        }
    }

    public static String decodeBinary(String binaryStr) {
        String[] binaryTokens = binaryStr.split(" ");
        StringBuilder result = new StringBuilder();
        for (String bin : binaryTokens) {
            int charCode = Integer.parseInt(bin, 2);
            result.append((char) charCode);
        }
        return result.toString();
    }

    public static String decodeHex(String hexStr) {
        String cleaned = hexStr.replaceAll("\\s+", "");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cleaned.length(); i += 2) {
            String byteStr = cleaned.substring(i, i + 2);
            int charCode = Integer.parseInt(byteStr, 16);
            result.append((char) charCode);
        }
        return result.toString();
    }

    public static String decodeBase64(String base64Str) {
        byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Str);
        return new String(decodedBytes);
    }

    public static String decodeASCII(String asciiStr) {
        String[] tokens = asciiStr.split(" ");
        StringBuilder result = new StringBuilder();
        for (String token : tokens) {
            int charCode = Integer.parseInt(token);
            result.append((char) charCode);
        }
        return result.toString();
    }

    
    
    
    
    
}
