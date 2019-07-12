package com.revature;
import java.util.Scanner;
import com.revature.bank.DataManager;

public class Interface {

    public static void main(String[] args) {
        
        clearScreen(); // for consistancy
        System.out.println("---CLI---");

        Scanner reader = new Scanner(System.in);  
        
        String s = "";
        while(!s.equals("exit")){
            System.out.print("> ");
            s = reader.nextLine(); 
            parseInput(s);
        }
        reader.close();
    }


    public static void parseInput(String cmd){
        

        
        switch (cmd) {
            case "clear":
                clearScreen();
                break;
            
            case "print":
                printTable();
                break;
            
            case "login":
                login();
                break;                
            case "exit":
                System.exit(0); // added for maven
                break;
            default:
                System.out.println("command not found: "+cmd);
        }
        


    }


    static void clearScreen() {  
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"+
                            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    } 

    static void printTable(){
        System.out.println("________________________");

        DataManager obj = new DataManager();
        obj.printTable();
        System.out.println("________________________");
    }

    static void login(){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.print:("Enter username: ");
        String userName = myObj.nextLine();  // Read user input
        

        System.out.print("Enter password: ");
        String password = myObj.nextLine();  // Read user input
        
    }

}