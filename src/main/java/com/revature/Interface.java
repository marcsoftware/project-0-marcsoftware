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
        
        //parse out args
        String[] args = cmd.split(" ");
        

        cmd = args[0];
        switch (cmd) {
            case "clear":
                clearScreen();
                break;
            
            case "print":
                printTable();
                break;
            
            case "login":
                login(args);
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

 

 




     /**
    The Desciption of the method to explain what the method does
    @param the parameters used by the method
    @return the value returned by the method
    @throws what kind of exception does this method throw
    */
    static void login(String[] args){
        
        String username=null,password=null;
        if(args.length==1){
        
            Scanner myObj = new Scanner(System.in);  
            System.out.print("Enter username: ");
            username = myObj.nextLine();  // Read user input
            
        }
        
        if(args.length==1 || args.length==2){
            
            Scanner myObj = new Scanner(System.in);  
            System.out.print("Enter password: ");
            password = myObj.nextLine();  // Read user input
            
        }
        
        if(args.length==3){

            username=args[1];
            password=args[2];
        }
        
        DataManager obj = new DataManager();
        obj.login(username,password);
        
    }

}