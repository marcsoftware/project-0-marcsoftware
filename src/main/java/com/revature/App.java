
package com.revature;
import java.util.Scanner;  // Import the Scanner class
/**
 * This is the interface that the customer will use.
 *
 */
public class App {
    public static void main(String[] args) {
        

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Registration Form:");
        System.out.println("enter username:");
        
        String userName = myObj.nextLine(); 
        System.out.println("Username is: " + userName); 
        System.out.println("enter password(must be at least 4 charecters long):"); 
        String userPassword = myObj.nextLine(); 
        System.out.println("password is: " + userPassword);
        
    }
}
