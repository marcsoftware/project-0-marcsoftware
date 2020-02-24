# Banking app
mimics banking function 

# HOW TO SETUP
```bash
// type the following commands
choco install postgresql  // During isntall set password to 'none' or else edit DataManager.java to reflect your custom password
```
```                          
mvn install:install-file -Dfile=c:/db/postgresql-42.2.6.jar -DgroupId=org.postgresql -DartifactId=postgresql -Dversion=42.2.6 -Dpackaging=jar
```
```
run src/resources/schema.sql

// create user for psql and set 
```

### how to change postgres password
```bash
psql postgres postgres
\password postgres
```

### how to run Schema.sql
```bash 
psql postgres     
\i Schema.sql    
```
# HOW TO RUN
```bash
// type the following commands
mvn compile
mvn exec:java -Dexec.mainClass="com.revature.App"
```
# HOW to use program
this is a command-line-program , so type the following commands:
```bash
login admin password   // login as the admin user, generated from schema.sql
clear                  // clears the screen
listaccounts           // lists bank accounts
login                  //  example   "login employee password"
logout                 // logs the user out
open                   // 
apply                  // apply for account,(accounts have to be approved before using)
approve                // [admin only]approve application for an account "approve 1234 10234" list ids to be approve separated by a space
reject                 // [admin only] reject application "reject 1234 2834" , list ids to be rejected

register               // register a new user "register marc_melcher thisismypassword"
printapps              // print applications (get ids from here to reject/approve)
deposit                // deposit money into account "deposit 1234 50" , deposits $50 into account#1234
withdraw               // width draw money  "withdraw 1234 50", withdraw 50 dollars
listaccounts           // list all of users account numbers 
transfer               // transfer "transfer 1234 7865 50" , withdraw $50 from acct#1234 and put in acct#7865
exit                   // gently shutdown the program
print                  // admin only - show all user info and hashed passwords
```

4e01ee046b9b4d898a867caea3cb39f0 