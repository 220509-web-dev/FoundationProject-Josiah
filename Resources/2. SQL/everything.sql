set search_path to user_data;
drop table if exists services;
drop table if exists users_private;
drop table if exists users;
drop schema if exists user_data;

create schema user_data;
set search_path to user_data;

create table users (
  user_id int generated always as identity,
  username varchar(255) unique not null,
  fname varchar(255) not null,
  lname varchar(255) not null,
  address1 varchar(255),
  address2 varchar(255),
  city varchar(255),
  state varchar(100),
  postalcode varchar(20),

  constraint users_pk
  primary key(user_id),
  constraint users_email_username
  check(username like '%@revature.net'),
  constraint users_username_length_min
  check(length(username) >= 14)

);

create table users_private (
  user_id int not null,
  social_sn varchar(9) check (length(social_sn) = 8),
  password varchar(255) check (length(password) >= 8) not null,
  
  constraint user_private_pk
  primary key(user_id),

  constraint user_private_fk
  foreign key (user_id)
  references users(user_id)

);

create table services (
  service_id int not null,
  description varchar(255)
);


insert into users (username,fname,lname,address1,address2,city,state,postalcode) values
('MJohnson76@revature.net','Marguerite','Johnson','2505 Southwest 43rd Street',null,'Oklahoma City','OK','73119'),
('GJones45@revature.net','Grover','Jones','5309 Darling Hill Road',null,'Burke','VT','05871'),
('BTorres31@revature.net','Blaine','Torres','38 Milford Street',null,'Medway','MA','02053'),
('JBueno86@revature.net','Justin','Bueno','6431 Shattuck Avenue','B','Oakland','CA','94609'),
('AYoung4@revature.net','Arthur','Young','1601 East 19th Street',null,'Edmond','OK','73013'),
('DTaylor63@revature.net','Daisy','Taylor','2269 Eastern Boulevard',null,'Montgomery','AL','36117'),
('MDyer94@revature.net','Margie','Dyer','42 Lake Lane',null,'Westmore','VT','05860'),
('HHenry87@revature.net','Hubert','Henry','189 Dalton Avenue',null,'Pittsfield','MA','01201'),
('RFoesch2@revature.net','Robert','Foesch','1314 89th Avenue',null,'Oakland','CA','94621');

insert into users_private (user_id,social_sn,password) values 
(1,52602647,'vtzLfxDhKBwIo53RdeN8'),
(2,87413077,'VwJdrFkQsacwN3ROmM7G'),
(3,42466503,'coSCy4i6dfvV36B7tdKF'),
(4,39899770,'Zt7S1R0yaXlZKSM1tygm'),
(5,57571894,'v5WsvNvKy8jlGbof9F4C'),
(6,58338399,'cpRu8zzYLfHFwEPuykhi'),
(7,56008405,'Mivv7WeEHDBCu0rYizIz'),
(8,87758416,'aP68mWp1lebKgDnWDqpo'),
(9,21435804,'MO4NVnf88tK9nR6n2Ulw');
