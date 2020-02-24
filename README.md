# Banking app
mimics banking function 

# HOW TO SETUP
```bash
 STEP ONE -   choco install postgresql
 STEP TWO - mvn install:install-file -Dfile=c:/db/postgresql-42.2.6.jar -DgroupId=org.postgresql -DartifactId=postgresql -Dversion=42.2.6 -Dpackaging=jar
 STEP THREE - run src/resources/schema.sql
```
# HOW TO RUN
```bash
mvn build
mvn exec:java -Dexec.mainClass="com.revature.App"
```
# HOW to use program
this is a command-line-program , so type the following commands:
```bash
clear                  // clears the screen
listaccounts           // lists registered accounts
login                  //  example   "login marc_melcher thisismypassword"
logout                 // logs the user out
"open":              
 "apply":
"approve":
 approve(args);
 "reject":
 reject(args);
"register":
register(args);
"printapps":
printApps();
 "deposit":
 deposit(args);
  "withdraw":
   withdraw(args);
 "transfer":
 transfer(args);
 "exit":
```

