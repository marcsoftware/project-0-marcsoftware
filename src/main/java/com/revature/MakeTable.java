package com.revature;
import java.sql.*;  

class MakeTable{  
//mvn install:install-file -Dfile=c:/db/postgresql-42.2.6.jar -DgroupId=org.postgresql -DartifactId=postgresql -Dversion=14.2.6 -Dpackaging=jar
    public static void main(String[] args) {

        // auto close connection
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test", "postgres", "none")) {

            if (conn != null) {
                System.out.println("Connected to the database!");
                printTable(conn);
            } else {
                System.out.println("Failed to make connection!");
            }

            conn.close();
            
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            
        }


    }


    public static void printTable(Connection conn){
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


