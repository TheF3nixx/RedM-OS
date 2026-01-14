package IO;

import Managers.ErrorHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.List;
import java.util.Scanner;

public class IO {
    private static final Scanner sc = new Scanner(System.in);
    private static final String CURRENTDIR = System.getProperty("user.dir");
    
    //Introducir datos
    public static String input(String text){
        System.out.print(text);
        return sc.nextLine();
    }
    
    public static int inputInt(String text){
        while(true){
            try{
                return Integer.parseInt(input(text));
            }catch(NumberFormatException e){
                ErrorHandler.trigger("001", "");
            }
        }
    }
    
    public static float inputFloat(String text){
        while(true){
            try{
                return Float.parseFloat(input(text));
            }catch(NumberFormatException e){
                ErrorHandler.trigger("001", "");
            }
        }
    }
    
    public static boolean inputYesNo(String text){
        String ans = input(text + " (Y to confirm): ");
        return ans.equals("Y") || ans.equals("y");
    }
    
    public static int inputIntRange(String text, int min, int max){
        while(true){
            int n = inputInt(text);
            if(n < min || n > max){
                ErrorHandler.trigger("002", "(" + min + "-" + max + ")");
            }else return n;
        }
    }
    
    public static int inputOption(String text, List<String> options){
        output(text);
        for(int i=0; i<options.size(); i++){
            output("  [" + i + "] " + options.get(i));
        }
        return inputIntRange("Insert option: ", 0, options.size()-1);
    }

    //Mostrar texto por pantalla
    public static void output(String text){
        System.out.println(text);
    }
    
    public static void error(String text){
        System.err.println(text);
    }
    
    public static void debug(String text){
        System.out.println("[DEBUG]> " + text);
    }
    
    //Mostrar avanzado
    public static void printSlow(String text, int delay) {
		for(char c : text.toCharArray()) {
			System.out.print(c);
			System.out.flush();
			try { sleep(delay); } catch (InterruptedException e) {}
		}
		System.out.println();
	}
	
	public static void loadingBar(int total, int delay) {
		for(int i = 0; i <= total; i++) {
			int percent = (i * 100) / total;

			String bar = "[";

			int filled = percent / 5;   // 20 bloques
			for(int j = 0; j < filled; j++) bar += "█";
			for(int j = filled; j < 20; j++) bar += " ";

			bar += "] " + percent + "%";

			System.out.print("\r" + bar); // redibujar en la misma l�nea
			System.out.flush();

			try { Thread.sleep(delay); } catch (InterruptedException e) {}
		}
		System.out.println();
	}
	
	public static void pulseLoader(String text, int cycles, int delay) {
		String[] frames = {"-", "\\", "|", "/"};

		for(int i = 0; i < cycles; i++) {
			String frame = frames[i % frames.length];
			System.out.print("\r" + text + " " + frame);
			System.out.flush();
			try { Thread.sleep(delay); } catch (InterruptedException e) {}
		}
		System.out.print("\r" + text + " \n");
	}
    
    public static void printInLine(String text){
        System.out.print(text);
    }
    
    public static void warn(String text){
        System.out.println("\u001B[33m[WARNING]> " + text + "\u001B[0m");
    }
    
    public static void fatal(String text){
        System.out.println("\u001B[41;97m[FATAL ERROR]> " + text + "\u001B[0m");
    }
    
    //Leer archivos
    public static void peruse(String filename) {
	File fileToView = new File(CURRENTDIR, filename);
	System.out.println("[DEBUG]> Searching in: " + fileToView.getAbsolutePath());

	if(!fileToView.exists()){
            ErrorHandler.trigger("003", filename);
            return;
	}
		
	if(fileToView.isDirectory()){
            ErrorHandler.trigger("004", filename);
            return;
	}
		
	try (BufferedReader br = new BufferedReader(new FileReader(fileToView))) {
            String line;
            while ((line = br.readLine()) != null) {
            //if (line.startsWith("#")) continue;//To not show tags
                System.out.println(line);
            }
        }catch(FileNotFoundException ex){
            ErrorHandler.trigger("005", filename);
	}catch(IOException ex){
            ErrorHandler.trigger("006", filename);
        }
    }

    //Otros
    public static void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void pause(String msg){
        input(msg + " (ENTER to continue)");
    }

}
