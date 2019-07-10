package com.revature;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
        System.out.println("---CLI---");

        Scanner reader = new Scanner(System.in);  
        
        String s = "";
        while(!s.equals("exit")){
            System.out.print("> ");
            s = reader.nextLine(); 
            
        }
        reader.close();
    }

}