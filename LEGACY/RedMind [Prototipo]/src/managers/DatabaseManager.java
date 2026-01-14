package managers;

import java.util.ArrayList;
import java.util.Scanner;
import otherThings.Connector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

         private static final Scanner sc = new Scanner(System.in);
         
         public static void start(){
              ArrayList<Object> vSub = new ArrayList<>();
              ArrayList<Object> iSub = new ArrayList<>(); 
              ArrayList<Object> uSub = new ArrayList<>(); 
              ArrayList<Object> dSub = new ArrayList<>(); 
              ArrayList<Object> dbMen = new ArrayList<>();
             int opc = 0, opc2 = 0;
             
             menuLoader(dbMen, vSub, iSub, uSub, dSub);

             
             do{
                 System.out.println("");
                 System.out.println("===== REDMIND DATABASE MANAGER =====");
                printMenu(dbMen);
                 System.out.println("[RMDB]> Insert option:");
                 opc = sc.nextInt();
                 
                 switch(opc){
                     case 1: 
                                System.out.println("===== VIEW =====");
                                printMenu(vSub);
                                System.out.println("[VIEW]> Insert option:");
                                opc2 = sc.nextInt();
                                switch(opc2){
                                    case 1: vUsers(); break;
                                    case 2: vPermissions(); break;
                                    case 3: vProjects(); break;
                                }
                         
                         break;
                     case 2: 
                                System.out.println("===== INSERT =====");
                                printMenu(iSub);
                                System.out.println("[INSERT]> Insert option:");
                                opc2 = sc.nextInt();
                                switch(opc2){
                                    case 1: iUsers(); break;
                                    case 2: iPermissions(); break;
                                    case 3: iProjects(); break;
                                }
                         
                         break;
                     case 3: 
                                System.out.println("===== UPDATE =====");
                                printMenu(uSub);
                                System.out.println("[UPDATE]> Insert option:");
                                opc2 = sc.nextInt();
                                switch(opc2){
                                    case 1: uUsers(); break;
                                    case 2: uPermissions(); break;
                                    case 3: uProjects(); break;
                                }
                         
                         break;
                     case 4: 
                                System.out.println("===== DELETE =====");
                                printMenu(dSub);
                                System.out.println("[DELETE]> Insert option:");
                                opc2 = sc.nextInt();
                                switch(opc2){
                                    case 1: dUsers(); break;
                                    case 2: dPermissions(); break;
                                    case 3: dProjects(); break;
                                }
                         
                         break;
                     case 0: 
                          System.out.println("[SYSTEM]> Exiting database...");
                          System.out.println("");
                         return;
                     default: System.err.println("Nop.");
                 }
                 
                 
             
             }while(opc != 0);
             
            
             
         }
         
         public static void menuLoader(ArrayList<Object> dbMen, ArrayList<Object> vSub, ArrayList<Object> iSub, ArrayList<Object> uSub, ArrayList<Object> dSub){ 

            iSub.add("1. Users");
            iSub.add("2. Permissions");
            iSub.add("3. Projects");
            iSub.add("0. Exit");
            
            uSub.add("1. Users");
            uSub.add("2. Permissions");
            uSub.add("3. Projects");
            uSub.add("0. Exit");
            
            dSub.add("1. Users");
            dSub.add("2. Permissions");
            dSub.add("3. Projects");
            dSub.add("0. Exit");
            
            
            vSub.add("1. Users");
            vSub.add("2. Permissions");
            vSub.add("3. Projects");
            vSub.add("0. Exit");

            dbMen.add("1. View");
            dbMen.add("2. Insert");
            dbMen.add("3. Update");
            dbMen.add("4. Delete");
            dbMen.add("5. Triggers"); 
            dbMen.add("0. Exit");
         }

    private static void printMenu(ArrayList<Object> dbMen) {
        for (Object option : dbMen) {
            System.out.println(option);
         }
    }
    
    //------------------------------------TODOS LOS VIEW--------------------------------------//
    
     private static void vUsers() {
        try (Connection conn = (Connection) Connector.getConnection();
         Statement stmt = (Statement) conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {

        System.out.println("\nUser list:");
        while (rs.next()) {
            System.out.printf("%d | %s | %s | %s%n",
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("role"),
                    rs.getBoolean("active") ? "1" : "0");
            
                  }

        } catch (SQLException e) {
            System.err.println("Couldn't fetch users: " + e.getMessage());
        }

    }

    private static void vPermissions() {
        try (Connection conn = (Connection) Connector.getConnection();
         Statement stmt = (Statement) conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM permissions")) {

        System.out.println("\nPermission list:");
        while (rs.next()) {
            System.out.printf("%d | %s | %s%n",
                    rs.getInt("id"),
                    rs.getString("user_id"),
                    rs.getString("permission_type"));
                  }

        } catch (SQLException e) {
            System.err.println("Couldn't fetch permissions: " + e.getMessage());
        }
    
    
    }

    private static void vProjects() {
        try (Connection conn = (Connection) Connector.getConnection();
         Statement stmt = (Statement) conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM projects")) {

        System.out.println("\nProject list:");
        while (rs.next()) {
            System.out.printf("%d | %s | %s | %s%n",
                    rs.getInt("id"),
                    rs.getString("project_name"),
                    rs.getString("status"),
                    rs.getInt("owner_id"));
                 }

        } catch (SQLException e) {
            System.err.println("Couldn't fetch projects: " + e.getMessage());
        }
    
    }
    
    private static void iUsers(){}

    private static void iPermissions() {}

    private static void iProjects() {}

    private static void uUsers() {}

    private static void uPermissions() {}

    private static void uProjects() {}

    private static void dUsers() {}

    private static void dPermissions() {}

    private static void dProjects() {}
    
    
    
        
}
