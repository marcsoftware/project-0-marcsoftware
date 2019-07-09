package com.revature;
import java.sql.*;  

class MakeTable{  
   public static void main(String args[]){  

      System.out.println("-------------------------------------------------------");
      System.out.println("-------------------------------------------------------");
      System.out.println("-------------------------------------------------------");
      System.out.println("-------------------------------------------------------");
      System.out.println("-------------------------------------------------------");
   try{  
      //mvn install:install-file -Dfile=c:/db/postgresql-42.2.6.jar -DgroupId=org.postgresql -DartifactId=postgresql -Dversion=14.2.6 -Dpackaging=jar
      Connection con=DriverManager.getConnection(   "jdbc:postgresql://localhost:5232/test?user=postgre&password=none");  
      //here sonoo is database name, root is username and password  
      
      
      
      
      con.close();  
   }catch(Exception e){ 
      //System.out.println( "mvn install:install-file -Dfile=c:/db/postgresql-42.2.6.jar -DgroupId=org.postgresql -DartifactId=postgresql -Dversion=14.2.6 -Dpackaging=jar";
      System.out.println(e);}  
   }  

   
   System.out.println("-------------------------------------------------------");
   System.out.println("-------------------------------------------------------");
   System.out.println("-------------------------------------------------------");
   System.out.println("-------------------------------------------------------");
   System.out.println("-------------------------------------------------------");
}  