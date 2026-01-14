package Managers;
//Esto es lo necesario para capturar y escribir los logs en el archivo de los logs del sistema
import Core.ErrorHandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogManager {
    private static final File logFile = new File("redmind/classified/syslog.rmi");
    
    public static void log(String action){
        try (FileWriter fw = new FileWriter(logFile, true);
             BufferedWriter bw = new BufferedWriter(fw)){
            String tstamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
            fw.write("[" + tstamp + "]> " + action);
            bw.newLine();
            
        }catch(IOException ex){
            ErrorHandler.trigger("RM-0012");
        }
    }
    
    public static void clearLog(){
        try{
            if(logFile.exists()){
                new FileWriter(logFile, false).close();//Esto se encarga de vaciar el archivo
                System.out.println("[SYSTEM]> Log cleared.");
            }
        }catch (Exception e){
            ErrorHandler.trigger("RM-0013");
        }
    }
    
    public static int getLogLineCount() {
     try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            int count = 0;
            while (br.readLine() != null){
                count++;
            }
            return count;
        } catch (IOException e) {
            return -1;
        }

    }
}
