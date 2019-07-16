CREATE TABLE account(
   user_id serial PRIMARY KEY,
   username VARCHAR (50) UNIQUE NOT NULL,
   password VARCHAR (50) NOT NULL,
   status varchar(20)
  
);

insert into account (user_id,username,password) values (0,'tomcruise','topgun');
insert into account (user_id,username,password,status) values (0,'admin','admin','admin');

CREATE TABLE applications (
    
   app_id  SERIAL, 
    PRIMARY KEY (app_id),
    
    owner_id int,
    coowner_id int,
    status varchar(20),

    FOREIGN KEY (owner_id) REFERENCES account(user_id),
    FOREIGN KEY (coowner_id) REFERENCES account(user_id)
); 

