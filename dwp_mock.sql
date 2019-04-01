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

select p.id, p.item_number, p.item_name, p.dwp_number, s.number as supplier from products p
join products_suppliers ps on p.id = ps.product_id
join suppliers s on ps.supplier_id = s.id;

/*ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '5Ht$v6BJp%z!&D';*/

