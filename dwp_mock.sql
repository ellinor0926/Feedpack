drop database dwp_mock;

create database dwp_mock;

use dwp_mock;

create table hfbs(
	id int primary key auto_increment,
    name varchar(30) not null
);

create table suppliers(
	id int primary key auto_increment,
    number char(5),
    name varchar(30)
);

create table products(
	id int primary key auto_increment,
    item_number char(6) not null,
    item_name varchar(30) not null,
	hfb_id int not null,
    dwp_number char(2) not null,
    foreign key (hfb_id) references hfbs(id)
);

create table products_suppliers(
	id int primary key auto_increment,
    product_id int not null,
    supplier_id int not null,
    foreign key (product_id) references products(id),
    foreign key (supplier_id) references suppliers(id)
);

create table types(
	id int primary key auto_increment,
    name varchar(30) not null
);

create table users(
	id int primary key auto_increment,
    first_name varchar(30),
    last_name varchar(30)
);

create table feedback(
	id int primary key auto_increment,
    product_id int not null,
    user_id int not null,
    comment varchar(200),
    foreign key (product_id) references products(id),
    foreign key (user_id) references users(id)
);

create table feedback_has_types(
	feedback_id int not null,
    type_id int not null,
    foreign key (feedback_id) references feedback(id),
    foreign key (type_id) references types(id)
);

insert into types (name)
values
('Consumer pack'), ('Multi pack'),
('Unit load'), ('Efficiency'),
('Ergonomic'), ('Inspiring');

insert into users (first_name, last_name)
values
('Ellinor', 'Danielsson'), ('Kalle', 'Svensson'); 

insert into hfbs (name)
values 
('Kitchen'), ('Decoration'), 
('Beds'), ('Lights');

insert into suppliers (number, name)
values 
('11111', 'A'), ('22222', 'B'), 
('33333', 'C'), ('44444', 'D');

insert into products (item_number, item_name, hfb_id, dwp_number)
values 
('123456', 'Glad', 1, '88'), 
('123456', 'Glad', 1, '89'), 
('234567', 'Fin', 2, '56'), 
('654321', 'Stark', 4, '32'), 
('987654', 'Mjuk', 3, '48'), 
('123456', 'Glad', 1, '90');

insert into products_suppliers (product_id, supplier_id)
values 
(1, 1), (2, 2), 
(3, 3), (4, 4), 
(5, 3), (6, 2);

insert into feedback (product_id, user_id, comment)
values
(1, 2, 'Lorem Ipsum'), (1, 1, 'Ipsum Lorem'),
(2, 2, 'Lorem upsum'), (3, 1, 'Lorem?');

insert into feedback_has_types (feedback_id, type_id)
values
(1, 5), (1, 3), (1, 4), (1, 6),
(2, 5), (3, 5), (3, 2), (4, 1);

select p.id, p.item_number, p.item_name, p.dwp_number, s.number as supplier from products p
join products_suppliers ps on p.id = ps.product_id
join suppliers s on ps.supplier_id = s.id;

select f.comment, t.name as type, u.first_name, u.last_name, p.item_name, p.item_number, p.dwp_number, s.number as supplier from feedback f
join products p on f.product_id = p.id
join feedback_has_types ft on f.id = ft.feedback_id
join types t on ft.type_id = t.id
join users u on f.user_id = u.id
join products_suppliers ps on p.id = ps.product_id
join suppliers s on ps.supplier_id = s.id
where f.product_id = 3;

select* from types;

/*ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '5Ht$v6BJp%z!&D';*/

