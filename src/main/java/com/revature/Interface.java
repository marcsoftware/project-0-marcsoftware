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
            case "exit":
                
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
        System.out.println("printTable");

        DataManager obj = new DataManager();
        obj.test();
    }

}