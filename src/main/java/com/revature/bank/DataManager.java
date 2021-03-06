package com.revature.bank;
import java.sql.*;  
import java.util.*;
import java.util.Arrays;
import java.util.regex.*;  
import java.security.*;
import java.math.BigInteger;



public class DataManager{  

    Connection conn;
    String username="";
    String password="";
    String id="";
    
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
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }

        
    }


    
    public void printTable(){
      
        if(!checkPermission(this.username,this.password)){
            System.out.println("You must be a admin or employee to do this.");
            return;
        }

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
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }

        
    }

    public void reject(String[] args){
        Boolean hasPermission = checkPermission(this.username,this.password);
        if(hasPermission){
            
        }else{
            System.out.println("You must be a admin or employee to do this.");
            return;
        }

        if(args.length==1){
            return;
        }
        String app_id=args[1];
        
        int beg = 1, end = args.length;
		String[] list_ids = new String[end - beg];
		System.arraycopy(args, beg, list_ids, 0, list_ids.length);
        


        //TODO for mat list_ids into (1,2,3)
        String formated_list="";
        String template= "'%s',";
        for(int i=0;i<list_ids.length;i++){
              
            formated_list+= String.format(template, list_ids[i]);

        }
        formated_list = formated_list.substring(0, formated_list.length() - 1); //delete the last comma
        
        String query= "UPDATE applications "+
        "SET status = 'rejected' "+
        "WHERE app_id IN (%s) and status='pending'; ";                

        
        query  = String.format(query, formated_list);
        
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            stmt.executeUpdate(query);
    
            stmt.close();
            

        }catch(Exception  e){
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }

    }

    private void createBankAccounts(String app_id){
        
        if(!checkPermission(this.username,this.password)){
            System.out.println("You must be a admin or employee to do this.");
            return;
        }

        String query= "UPDATE applications "+
        "SET status = 'approved' "+
        "WHERE app_id IN (%s) and status='pending'; ";        

        
        query = String.format(query, app_id);
       execute(query);
        
        //
        
        String max = getLastAccountNumber(); 
        
        if(max == "null"){
            max="0";
        }
        int new_account_number=Integer.parseInt(max)+101;
        
        query= "insert into bank(account_number,balance) values(%s,0) ";        

        
        query = String.format(query, new_account_number);
        execute(query);

       

        query= "INSERT INTO bank_owners(account_number,owner_id) VALUES "+
                      "(  %s,"+
                      " (SELECT owner_id FROM applications  WHERE app_id='%s'));";    
        
        query = String.format(query, new_account_number,app_id);
        execute(query);

        String coowner_id=getJointOwner(app_id);

        if(coowner_id==null){
            
        }else{
            query= "INSERT INTO bank_owners(account_number,owner_id) VALUES "+
                      "( %s,%s);";    
        
        query = String.format(query, new_account_number,coowner_id);
        execute(query);

        
        }
    }

   public void deposit(String[] args){
        if(args.length<3){
            return; //not enough args
        }

        String account_number=(args[1]);
        String money = (args[2]);
        money = money.replace("-", ""); //assume that user want positive
        

        if(isOwner(account_number)){
            
        }else{
            System.out.println("You do not own account#: "+account_number);
            return; 
        }


        //save tranaction to history
        String query= "insert into history(name,type,amount,account_number) values('%s','%s','%s','%s') ;";

        query = String.format(query,this.username, "deposit" ,money,account_number);
        execute(query);
        //
         query= "UPDATE bank "+
            "SET balance = balance +%.2f  "+
            "WHERE account_number='%s' ";        

            float fMoney = Float.parseFloat(money);
            query = String.format(query,fMoney ,account_number);
        execute(query);

        
    }

    public void transfer(String[] args){
        if(args.length<4){
            System.out.println("failed: you are missing args.");
            return; //not enough args
        }
       
        String from_account = args[1];
        String to_account = args[2];
        String amount = args[3];
 
        
        args[2]=amount;
        if(withdraw(args)){

        }else{
            return; // 
        }

        args[1]=to_account;
        
        deposit(args);

    }
   
   public boolean withdraw(String[] args){
            if(args.length<3){
                return false; //not enough args
            }

            String account_number=(args[1]);
            String money = (args[2]);
            money = money.replace("-", ""); //assume that user want positive
            
            
        

            if(isOwner(account_number)){
                
            }else{
                System.out.println("You do not own account#: "+account_number);
                return false; 
            }

            String balance = getBalance(account_number);
            float fBalance = Float.parseFloat(balance);
            float fMoney = Float.parseFloat(money);
            if(fMoney >= fBalance){
                System.out.println("tansaction failed: insufficient funds");
                return false;
            }

            String query= "UPDATE bank "+
                "SET balance = balance - %.2f "+
                "WHERE account_number='%s' ";        

                
                //float fMoney = Float.parseFloat(money);
                query = String.format(query,fMoney ,account_number);
            execute(query);

             //save tranaction to history
         query= "insert into history(name,type,amount,account_number) values('%s','%s','%s','%s') ;";

        query = String.format(query,this.username, "withdraw" ,money,account_number);

            
            
        execute(query);

        //

            return true;
        }


   private boolean isOwner(String account_number){
        String query= "select owner_id from bank_owners where owner_id='%s';";    

        query = String.format(query, this.id);
        
        boolean result=false;
        Statement stmt; 
        
        
        try{
            
            stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            
        
            
            if (rs.next()) {
                
                
                
                
               result=true;
                
                
            }else{
                result=false;
                
            }

            stmt.close();
            rs.close();

        }catch(Exception  e){
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }
        
        return result;
        
   }

   private String getBalance(String account_number){
    String query= "select balance from bank where account_number='%s';";    

    query = String.format(query, account_number);
    
    String result="0";
    Statement stmt; 
    

    
    try{
        
        stmt = conn.createStatement(); 
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();

        
    
        
        if (rs.next()) {
            
            
           result=rs.getString("balance");    
            
    
            
            
        }else{
            result="0";
            
        }

        stmt.close();
        rs.close();

    }catch(Exception  e){
        System.err.format("ERROR: \n%s\n", e.getMessage());
    }finally{
        
    }
    
    return result;
    
}


    private String getLastAccountNumber(){
        String query = "SELECT MAX(account_number) FROM bank ;";
        String max="0";
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
    
            if (rs.next()) {
                
                 max = rs.getString(1);
                
            }else{
                max="0";
            }

            stmt.close();
            
            

        }catch(Exception  e){
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }

        return max;
        
    }


    private String getJointOwner(String app_id){
        String query = "SELECT coowner_id FROM applications where app_id='%s';";
        String owner="";
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            query = String.format(query,app_id);
            ResultSet rs = stmt.executeQuery(query);
    
            if (rs.next()) {
                
                owner = rs.getString("coowner_id");
                
            }

            stmt.close();
            
            

        }catch(Exception  e){
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }

        return owner;
        
    }


    private void execute(String query){
        
        
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            stmt.executeUpdate(query);
    
            stmt.close();
            

        }catch(Exception  e){
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }


    }


    

    public void approve(String[] args){
        
        if(!checkPermission(this.username,this.password)){
            System.out.println("You must be a admin or employee to do this.");
            return;
        }

        
        if(args.length==1){
            return;
        }
        String app_id=args[1];
        
        int beg = 1, end = args.length;
		String[] list_ids = new String[end - beg];
		System.arraycopy(args, beg, list_ids, 0, list_ids.length);
        


        //TODO for mat list_ids into (1,2,3)
        String formated_list="";
        String template= "'%s',";
        for(int i=0;i<list_ids.length;i++){
              
            formated_list+= String.format(template, list_ids[i]);

        }
        formated_list = formated_list.substring(0, formated_list.length() - 1); //delete the last comma
   
        
        String query= "select * from applications "+
        "WHERE app_id IN (%s) and status='pending' ; ";                

        
        query  = String.format(query, formated_list);
        
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            
            ResultSet rs = stmt.executeQuery(query);
            //
            
            while (rs.next()) {
                String id = rs.getString("app_id");
                
                createBankAccounts(id);
                
            }
            //

            stmt.close();
            
            System.out.println("approved");

        }catch(Exception  e){
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }

        
    }

    public void printApps(){

        Boolean hasPermission = checkPermission(this.username,this.password);
        if(hasPermission){
            
        }else{
            System.out.println("You must be a admin or employee to do this.");
            return;
        }


        
        String query = "select * from applications";
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            
           
            System.out.println("----------------------------------");
            while (rs.next()) {
                String id = rs.getString("app_id");
                String coowner = rs.getString("coowner_id");
                String owner = rs.getString("owner_id");
                String status = rs.getString("status");
                System.out.println(id+" : "+owner+" : "+coowner +" : "+status+ "\n");
            }
            System.out.println("----------------------------------");
            stmt.close();
            rs.close();

        }catch(Exception  e){
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }

        
    }


    public void listAccounts(){

        //String query = "select * from bank_owners where owner_id='%s';";

        String query = "SELECT * "+
                       "FROM bank_owners "+
                       "INNER JOIN bank "+
                       "ON bank.account_number = bank_owners.account_number "+
                       "where owner_id='%s' ;";

                      
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            query  = String.format(query, this.id);
            
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            
           
            System.out.println("----------------------------------");
            while (rs.next()) {
                
                
                
                String account_number = rs.getString("account_number");
                String balance = rs.getString("balance");
                System.out.println(account_number+" : "+balance+ "\n");
            }
            System.out.println("----------------------------------");
            stmt.close();
            rs.close();

        }catch(Exception  e){
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }

        
    }


    public String login(String username,String password){

          //
          String data = password;
          
          MessageDigest messageDigest;
          try {
              messageDigest = MessageDigest.getInstance("MD5");
              messageDigest.update(data.getBytes());
              byte[] messageDigestMD5 = messageDigest.digest();
              StringBuffer stringBuffer = new StringBuffer();
              for (byte bytes : messageDigestMD5) {
                  stringBuffer.append(String.format("%02x", bytes & 0xff));
              }
   
              // password=stringBuffer.toString(); // disbable passowrd hasing
          } catch (NoSuchAlgorithmException exception) {
              // TODO Auto-generated catch block
              exception.printStackTrace();
          }
          //

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
                this.password = password;
                this.id=id;
            }else{
                System.out.println("username or password was incorrect.");
            }

            stmt.close();
            rs.close();

        }catch(Exception  e){
            System.err.format("ERROR: \n%s\n", e.getMessage());
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
            
        }else{
            addNewApplication(this.username);
        }
        
        System.out.println("Your application has been submitted.");
    }

    public void register(String username,String password){
        
        if(usernameExsists(username)){
            System.out.println("that username already exsists.");
            return;
        }else{
            System.out.println("new user registered.");
        }

        //
        String data = password;
        
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes());
            byte[] messageDigestMD5 = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte bytes : messageDigestMD5) {
                stringBuffer.append(String.format("%02x", bytes & 0xff));
            }
 
       
            //password=stringBuffer.toString(); // this line hashed the password, but i comment it out
            
        } catch (NoSuchAlgorithmException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        }
        //
           
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
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }

        
    }

    public void addNewApplication(String owner,String coowner){
        String query= "INSERT INTO applications(owner_id,coowner_id,status) VALUES "+
                      "  ((SELECT user_id FROM account WHERE username='%s'),"+
                      " (SELECT user_id FROM account  WHERE username='%s'),'pending');";                

        
        query  = String.format(query, owner,coowner);
        
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            stmt.executeUpdate(query);
    
            stmt.close();
            

        }catch(Exception  e){
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }

        
    }

    //overloaded
    public void addNewApplication(String owner){
        String query= "INSERT INTO applications(owner_id,status) VALUES "+
                      "  ((SELECT user_id FROM account WHERE username='%s'),'pending')";                

        
        query  = String.format(query, owner);
        
        Statement stmt; 
        try{
            
            stmt = conn.createStatement(); 
            stmt.executeUpdate(query);
    
            stmt.close();
            

        }catch(Exception  e){
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }

        
    }

    public Boolean authenticate(String username,String password){

        //
        String data = password;
          
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes());
            byte[] messageDigestMD5 = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte bytes : messageDigestMD5) {
                stringBuffer.append(String.format("%02x", bytes & 0xff));
            }
 
            password=stringBuffer.toString();
        } catch (NoSuchAlgorithmException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        }
        //



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
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }

        return result;

    }


    public Boolean checkPermission(String username,String password){


        String query = "select status from account where username='%s' and password='%s'"; //TODO change to prepared statment
        query  = String.format(query, username,password);
        Statement stmt; 
        Boolean result=false;
        
        try{
            
            stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            
        
            
            if (rs.next()) {
                
                
                String status = rs.getString("status");
                
                if(status.equals("admin") || status.equals("employee")){
                    result=true;
                }

                
                
            }else{
                result=false;
                
            }

            stmt.close();
            rs.close();

        }catch(Exception  e){
            System.err.format("ERROR: \n%s\n", e.getMessage());
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
            System.err.format("ERROR: \n%s\n", e.getMessage());
        }finally{
            
        }

        return result;

    }
    
} 


