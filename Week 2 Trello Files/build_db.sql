drop table if exists services;
drop table if exists users_private;
drop table if exists users;
drop schema if exists user_data;
create schema user_data;
set search_path to user_data;



create table users (
  user_id int generated always as identity,
  username varchar(255) not null check(length(username) >=2),
  fname varchar(255) not null,
  lname varchar(255) not null,
  address1 varchar(255),
  address2 varchar(255),
  city varchar(255),
  state varchar(100),
  postalcode varchar(20),

  constraint users_pk
  primary key(user_id)

);

create table users_private (
  user_id int not null,
  social_sn varchar(9) check (length(social_sn) = 8),
  password varchar(255) check (length(password) >= 8),
  
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