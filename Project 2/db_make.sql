set search_path to notecard;
drop table if exists ratings;
drop table if exists card_deck;
drop table if exists cards;
drop table if exists decks;
drop table if exists userp;
drop table if exists users;
drop table if exists roles;
drop schema if exists notecard;
create schema notecard;
set search_path to notecard;

create table roles (
	role_id int unique not null
,	title varchar(32) not null
);

create table users (
	id int generated always as identity
,	username varchar(32) not null
,	fname varchar (16) not null
,	lname varchar (16) not null
,	role int not null default 1
,	creationdate varchar(20) default current_time
,	creationtime varchar(20) default current_time
	
,	constraint users_pkey
	primary key(id)
	
,	constraint users_email_username
	check(username like '%revature.net' 
	or username like '%Revature.net')
	
,	constraint users_username_length_min
	check(length(username) >= 15)

,	constraint user_roles_fkey
	foreign key (id)
	references roles(role_id)
);
CREATE UNIQUE INDEX username_unique_idx on users (LOWER(username));  



create table userp (
	id int primary key
,	password varchar(64) not null

,	constraint userp_fkey
	foreign key (id)
	references users(id)
);

create table decks (
	deck_id int generated always as identity
,	owner_id int
,	deckname varchar(32) not null

,	constraint decks_pkey
	primary key(deck_id)
,	constraint user_private_fkey
	foreign key (owner_id)
	references users(id)
); -- many decks to 1 user

create table cards (
	id int generated always as identity
,	html_q varchar not null
,	html_a varchar
	
,	constraint cards_pkey
	primary key(id)
);

-- many-to-many relationship between cards and decks
CREATE TABLE card_deck (
  card_id    int REFERENCES cards (id) ON UPDATE CASCADE ON DELETE CASCADE not null
, deck_id int REFERENCES decks (deck_id) ON UPDATE CASCADE not null
, CONSTRAINT card_deck_pkey PRIMARY KEY (card_id, deck_id)  -- explicit pk
);

create table ratings (
	card_id int not null
,	user_id int not null
,	seeagain bool not null default true
,	rating real check(0<=rating and rating<=10) not null
,	creationdate varchar(20) default current_time
,	creationtime varchar(20) default current_time
	
,	constraint ratings_fkey1
	foreign key (card_id)
	references cards(id)	
,	constraint ratings_fkey2
	foreign key (user_id)
	references users(id)
);

-- set keys for ratings table


/* Insert example data */

insert into roles values (1,'awaiting approval'), (2, 'basic user'), (2,'Admin');

insert into users values 
	(default, 'Tester@revature.net', 'Tester', 'Testerson', default, default, default);

insert into userp values 
	((select id from users where lower(username) like '%tester%' limit 1), 'abcd');

--The following shows user pairing with their password
--select u.id, u.username, u.fname, u.lname, up.password from users u inner join userp up on u.id = up.id;

insert into cards values 
	(default, '<h1>What is SQL</h1>', '<h1>Structured Query Language</h1>')
,	(default, '<h1>What is DDL</h1>', '<h1>Data Definition Language</h1>
	<ul>
	<li>create</li>
	<li>alter</li>
	<li>truncate</li>
	<li>drop</li>
	</ul>')
,	(default, '<h1>What is TCL</h1>', '<h1>Transaction Control Language</h1>
	<ul>
	<li>savepoint</li>
	<li>commit</li>
	<li>rollback</li>
	</ul>')
,	(default, '<h1>What is DML</h1>', '<h1>Data Manipulation Language</h1>
	<ul>
	<li>select</li>
	<li>insert</li>
	<li>update</li>
	<li>delete</li>
	</ul>')
,	(default, '<h1>What is DCL</h1>', '<h1>Data Control Language</h1>
	<ul>
	<li>grant</li>
	<li>revoke</li>
	</ul>'
);

insert into cards values 
	(default, '<h1>What is Java</h1>', '<p>Java is a general-purpose, class-based, object-oriented programming language designed for having lesser implementation dependencies.</p>')
,	(default, '<h1>In what way does Java employ abstraction?</h1>', '<p>Abstraction refers to the quality of dealing with ideas rather than events. It basically deals with hiding the details and showing the essential things to the user. Thus you can say that abstraction in Java is the process of hiding the implementation details from the user and revealing only the functionality to them.</p>')
,	(default, '<h1>In what way does Java employ polymorphism?</h1>', '<p>Polymorphism is briefly described as “one interface, many implementations”. Polymorphism is a characteristic of being able to assign a different meaning or usage to something in different contexts.</p>')
,	(default, '<h1>In what way does Java employ inheritance?</h1>', '<p>Inheritance in Java is the concept where the properties of one class can be inherited by the other. It helps to reuse the code and establish a relationship between different classes.</p>')
,	(default, '<h1>In what way does Java employ encapsulation?</h1>', '<p>Encapsulation is a mechanism where you bind your data(variables) and code(methods) together as a single unit.</p>')
;

-- Make decks
insert into decks (values 
	(default, (select id from users where lower(username) like '%tester%' limit 1), 'SQL Deck')
,	(default, (select id from users where lower(username) like '%tester%' limit 1), 'Java Deck')
);


-- Sets up 2 decks
insert into card_deck 
select id, (select deck_id from decks where lower(deckname) like '%sql%' limit 1) 
from cards where html_a like '%Language%'
union
select id, (select deck_id from decks where lower(deckname) like '%java%' limit 1)
from cards where lower(html_q) like '%java%'
order by id;


/* Example select statement */

-- Select id, question, and answer from cards table
select id "Card ID", replace(html_q,E'\n\t', '') "Question", replace(html_a,E'\n\t', '') "Answer" from cards;

-- show amount of cards in each deck
select deck_id, count(*) "Number of Cards" from card_deck group by deck_id;

-- Get Tester's cards
select id "Card ID", replace(html_q,E'\n\t', '') "Question", replace(html_a,E'\n\t', '') "Answer" from cards
where id in
(select card_id from card_deck where deck_id in
(select deck_id from decks where owner_id in 
(select id from users where lower(username) like '%tester%' limit 1))) order by id;


