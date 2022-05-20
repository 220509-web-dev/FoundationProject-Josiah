drop table if exists "Transactions"; 
drop table if exists "Services";
drop table if exists "Users_private";
drop table if exists "Users";


create table "Users" (
  "user_id" int generated always as identity primary key,
  "Address1" varchar(255),
  "Address2" varchar(255),
  "City" varchar(255),
  "State" varchar(255),
  "PostalCode" varchar(255),
  "Name" varchar(255) not null
);

create table "Users_private" (
  "user_id" int not null,
  "social_sn" varchar(9),
  "password" varchar(255),
  primary key (user_id),
  foreign key (user_id) references "Users"(user_id)
);

create table "Services" (
  "service_id" int not null,
  "description" varchar(255)
);

create table "Transactions" (
  "transaction_id" int generated always as identity primary key,
  "user_id" int not null,
  "price" int not null,
  "amount_of_service" int not null,
  "paid" boolean not null,
  foreign key (user_id) references "Users"(user_id)
);
