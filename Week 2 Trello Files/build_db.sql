drop table if exists transactions;
drop table if exists services;
drop table if exists users_private;
drop table if exists users;

create table "users" (
  "user_id" int generated always as identity primary key,
  "address_physical" varchar(255),
  "address_billing" varchar(255),
  "full_name" varchar(255) not null
);

create table "users_private" (
  "user_id" int not null,
  "social_sn" varchar(9),
  "password" varchar(255),
  primary key (user_id),
  foreign key (user_id) references users(user_id)
);

create table "services" (
  "service_id" int not null,
  "description" varchar(255)
);

create table transactions (
  "transaction_id" int generated always as identity primary key,
  "user_id" int not null,
  "price" int not null,
  "amount_of_service" int not null,
  "paid" boolean not null,
  foreign key (user_id) references users(user_id)
);



/*
select * from users;
select * from users_private;
*/

