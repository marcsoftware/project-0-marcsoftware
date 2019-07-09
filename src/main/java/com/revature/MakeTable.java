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

            Connection conn = DriverManager.getConnection( 
                  "jdbc:postgre://localhost:5232/test?user=postgre&password=none"
               );

            //String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
            //Connection conn = DriverManager.getConnection(url);
            //Class.forName("com.mysql.jdbc.Driver");  
            
            conn.close();  

      }catch(Exception e){ 
            System.out.println(e);
            System.out.println("not Connected?");
      }  
       

   
   System.out.println("-------------------------------------------------------");
   System.out.println("-------------------------------------------------------");
   System.out.println("-------------------------------------------------------");
   System.out.println("-------------------------------------------------------");
   System.out.println("-------------------------------------------------------");
}  

}