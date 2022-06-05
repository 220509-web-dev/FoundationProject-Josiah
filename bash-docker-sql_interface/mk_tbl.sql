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



insert into users (address_physical, address_billing, full_name) 
values('1 Lane, SA, TX', 'PO Box 9','J Doe'),
('3 Bvld, SA, TX', 'PO Box 200','First Last');

insert into users_private (user_id, social_sn,password)
values(1,'123456789','AS3klewrgeglwppfj3g04ij3'),
(2,'987654321','ernrn3r3r7Twrn3krlwspfsd');

insert into services (service_id,description)
values (1, '1-hour computer repair'),
(2, '1-hour consulting'),
(3, 'misc service');




