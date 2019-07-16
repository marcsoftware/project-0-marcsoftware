package com.revature.bank;
import java.sql.*;  
import java.util.*;
public class DataManager{  

    Connection conn;
    String username="";
    Object password;

    
    public DataManager() {

        // auto close connection
        try  {

             conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test", "postgres", "none");

            if (conn != null) {
                
                
            } else {
                System.out.println("Failed to make connection!");
            }

            
            
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.getMessage();
        }finally{
            
        }


    }


    public void print(){
        System.out.println("printing table.");
        String query = "select * from test";
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            System.out.println("META:"+rsmd);
           
            
            while (rs.next()) {
                String year = rs.getString("year");
                System.out.println(year + "\n");
            }

            stmt.close();
            rs.close();

        }catch(Exception  e){
            System.err.format("ERROR: \n%s", e.getMessage());
        }finally{
            
        }

        
    }


    
    public void printTable(){
        
        String query = "select * from account";
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            
           
            
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String id = rs.getString("user_id");
                System.out.println(id + "  "+username+"  "+password);
            }

            stmt.close();
            rs.close();

        }catch(Exception  e){
            System.err.format("ERROR: \n%s", e.getMessage());
        }finally{
            
        }

        
    }

    public String login(String username,String password){

        String query = "select user_id from account where username='%s' and password='%s'"; //TODO change to prepared statment
        query  = String.format(query, username,password);
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            
           
            
            if (rs.next()) {
                
                
                String id = rs.getString("user_id");
                System.out.println("logged in as "+username);
                System.out.println("your id is: "+id);
                
                this.username = username;
                this.password = password.toCharArray();
            }else{
                System.out.println("username or password was incorrect.");
            }

            stmt.close();
            rs.close();

        }catch(Exception  e){
            System.err.format("ERROR: \n%s", e.getMessage());
        }finally{
            
        }

        return username;

    }

    public void logout(){
        this.username="";
        
    }

    public void open(){
        System.out.println(".........");
        
    }

    public void apply(){
        
        
        //check if logged in first
        if(this.username.length()>0){

            
        }else{
            System.out.println("You must login before applying.");
            return;
        }

        //is this a joint account application?
        Scanner myObj = new Scanner(System.in);  
        System.out.println("Is this for a joint account?(y/n):");

        String response = myObj.nextLine(); 
        
        //if yes
        if(response.equals("y")){
            System.out.print("Enter co-owner's username:");

            String co_username = myObj.nextLine(); 

            System.out.print("Enter co-owner's password:");

            String co_password = myObj.nextLine(); 

            //TODO authenticate...
            if(authenticate(co_username, co_password)){
                //
            }else{
                System.out.println("wrong co-owner username or co-owner password.");
                return;
            }
            //
            addNewApplication(this.username,co_username);
            System.out.println("Your application has been submitted.");
        }
        
    }

    public void register(String username,String password){
        
        if(usernameExsists(username)){
            System.out.println("that username already exsists.");
            return;
        }else{
            System.out.println("new user registered.");
        }


           
        addNewUser(username,password);

    }

    public void addNewUser(String username,String password){
        String query = " insert into account (username,password) values ('%s','%s');"; //TODO change to prepared statment
        query  = String.format(query, username,password);
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            stmt.executeUpdate(query);
    
            stmt.close();
            

        }catch(Exception  e){
            System.err.format("ERROR: \n%s", e.getMessage());
        }finally{
            
        }

        
    }

    public void addNewApplication(String owner,String coowner){
        String query= "INSERT INTO applications(owner_id,coowner_id) VALUES "+
                      "  ((SELECT user_id FROM account WHERE username='%s'),"+
                      " (SELECT user_id FROM account  WHERE username='%s'));";                

        
        query  = String.format(query, owner,coowner);
        
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            stmt.executeUpdate(query);
    
            stmt.close();
            

        }catch(Exception  e){
            System.err.format("ERROR: \n%s", e.getMessage());
        }finally{
            
        }

        
    }

    public Boolean authenticate(String username,String password){
        String query = "select user_id from account where username='%s' and password='%s'"; //TODO change to prepared statment
        query  = String.format(query, username,password);
        Statement stmt; 
        Boolean result=false;
        try{
            
            stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            
           
            
            if (rs.next()) {
                
                
                String id = rs.getString("user_id");
                
                result=true;
                
            }else{
                result=false;
            }

            stmt.close();
            rs.close();

        }catch(Exception  e){
            System.err.format("ERROR: \n%s", e.getMessage());
        }finally{
            
        }

        return result;

    }

    public Boolean usernameExsists(String username){
        boolean result=false;
        String query = "select user_id from account where username='%s' "; //TODO change to prepared statment
        query  = String.format(query, username);
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            
           
            
            if (rs.next()) {
                
                
                
                
                result=true;
                //this.login_name=username;
            }else{
                //
            }

            stmt.close();
            rs.close();

        }catch(Exception  e){
            System.err.format("ERROR: \n%s", e.getMessage());
        }finally{
            
        }

        return result;

    }
    
} 


