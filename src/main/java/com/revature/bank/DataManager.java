package com.revature.bank;
import java.sql.*;  

public class DataManager{  

    Connection conn;
    public DataManager() {

        // auto close connection
        try  {

             conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test", "postgres", "none");

            if (conn != null) {
                System.out.println("Connected to the database!");
                
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


    public void register(){
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

        System.exit(0); // added for maven
    }


    
    public void printTable(){
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

        System.exit(0); // added for maven
    }
    
} 


