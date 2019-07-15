package com.revature.bank;
import java.sql.*;  

public class DataManager{  

    Connection conn;

    private String login_name=null;
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
                this.login_name=username;
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
        this.login_name="";
        
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


