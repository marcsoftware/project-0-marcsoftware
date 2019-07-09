package com.revature;
import java.sql.*;  

class MakeTable{  
//mvn install:install-file -Dfile=c:/db/postgresql-42.2.6.jar -DgroupId=org.postgresql -DartifactId=postgresql -Dversion=14.2.6 -Dpackaging=jar
public static void main(String[] args) {

   // https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html#package.description
   // auto java.sql.Driver discovery -- no longer need to load a java.sql.Driver class via Class.forName

   // register JDBC driver, optional, since java 1.6
   /*try {
       Class.forName("org.postgresql.Driver");
   } catch (ClassNotFoundException e) {
       e.printStackTrace();
   }*/
   
   // auto close connection
   try (Connection conn = DriverManager.getConnection(
           "jdbc:postgresql://localhost:5432/test", "postgres", "none")) {

       if (conn != null) {
           System.out.println("Connected to the database!");
       } else {
           System.out.println("Failed to make connection!");
       }

   } catch (SQLException e) {
       System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
   } catch (Exception e) {
       e.printStackTrace();
   }finally{
      
   }


}
}


