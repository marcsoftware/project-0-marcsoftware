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
      Class.forName("com.mysql.jdbc.Driver");  
      Connection con=DriverManager.getConnection(  
         "jdbc:mysql://localhost:80/sonoo","root","root");  
      //here sonoo is database name, root is username and password  
      Statement stmt=con.createStatement();  
      ResultSet rs=stmt.executeQuery("select * from emp");  
      while(rs.next())  
      System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
      con.close();  
   }catch(Exception e){ System.out.println(e);}  
   }  
}  