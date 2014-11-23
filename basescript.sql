create database project3;

use project3;

create table Item
	(upc int not null,
 	title varchar(40) not null,
	type varchar(20) not null,
	category varchar(40) not null,
	company  varchar(40) not null,
	year int not null,
	price int not null,
	stock int not null);

alter table Item
add primary key (upc);

create table Customer
	(cid int not null,
	 password varchar(40) not null,
	 name varchar(40) not null,
	 address varchar(40) not null,
	 phone varchar(40) not null);

alter table Customer 
add primary key (cid);



create table LeadSinger
	(upc int not null,
	name varchar(40) not null);

alter table LeadSinger
add primary key (upc, name);

alter table LeadSinger
add foreign key (upc) references Item (upc);


create table HasSong
	(upc int not null,
	title varchar(40) not null);

alter table HasSong
add primary key (upc, title);

alter table HasSong
add foreign key (upc) references Item (upc);


create table Orders 
	(receiptId int not null,
	 dates date not null,
	 cid int not null,
	 cardNumber varchar(40) not null,
	 expiryDate date not null,
	 expectedDate date not null,
	 deliveredDate date not null);

alter table Orders 
add primary key (receiptId);

alter table Orders
add foreign key (cid) references Customer (cid);


create table PurchaseItem
	(receiptId int not null,
	 upc int not null,
	 quantity int not null);

alter table PurchaseItem 
add primary key (receiptId, upc);

alter table PurchaseItem
add foreign key (upc) references Item (upc);

alter table PurchaseItem
add foreign key (receiptId) references Orders (receiptId);	 


create table Returns
	(retId int not null,
	 dates date not null,
	 receiptId int not null);

alter table Returns 
add primary key (retId);

alter table Returns
add foreign key (receiptId) references Orders (receiptId);


create table ReturnItem
	(retId int not null,
	 upc int not null,
	 quantity int not null);

alter table ReturnItem 
add primary key (retId, upc);

alter table ReturnItem
add foreign key (upc) references Item (upc);

alter table ReturnItem
add foreign key (retId) references Returns (retId);



insert into Customer values
(300, 'Firebolt', 'Steve', '2222 Broadway', 6041234567);

insert into Item  values
(100, 'Frozen', 'CD', 'rock', 'Disney', 2014, 10, 100);
insert into Item  values
(101, 'Tarzan', 'CD', 'pop', 'Disney', 2013, 10, 100);
insert into Item  values
(102, 'Wayne', 'CD', 'rap', 'Merica', 2012, 10, 100);
insert into Item  values
(103, 'Farm', 'DVD', 'country', 'Gang', 2014, 10, 100);
insert into Item  values
(104, 'Frozen', 'CD', 'classical', 'Disney', 2013, 10, 100);
insert into Item  values
(105, 'Frozen', 'CD', 'new age', 'Disney', 2012, 10, 100);
insert into Item  values
(106, 'Ride 1', 'DVD', 'instrumental', 'Wbros', 2011, 10, 100);
insert into Item  values
(107, 'Ride 2', 'DVD', 'rock', 'Wbros', 2013, 10, 100);
insert into Item  values
(108, 'Ride 3', 'DVD', 'rock', 'Wbros', 2012, 10, 100);


insert into LeadSinger values
(100, 'Kristen Bell');
	insert into LeadSinger values
	(101, 'Singer 1');
insert into LeadSinger values
(102, 'singer 2');
insert into LeadSinger values
(103, 'singer 3');
insert into LeadSinger values
(104, 'singer 4');
insert into LeadSinger values
(105, 'singer 5');
insert into LeadSinger values
(106, 'singer 6');


insert into HasSong  values
(107, 'Let it go');
insert into HasSong  values
(108, 'Beat it');


insert into Orders values
(500, '2014-01-01', 300, '80808080', '2015-01-01', '2014-02-02', '2014-02-02');
insert into Orders values
(501, '2014-01-01', 300, '80808081', '2015-01-01', '2014-02-02', '2014-02-02');
insert into Orders values
(502, '2014-01-02', 300, '80808082', '2015-01-01', '2014-02-02', '2014-02-02');
insert into Orders values
(503, '2014-01-03', 300, '80808083', '2015-01-01', '2014-02-02', '2014-02-02');
insert into Orders values
(504, '2014-01-04', 300, '80808084', '2015-01-01', '2014-02-02', '2014-02-02');
insert into Orders values
(505, '2014-01-01', 300, '80808084', '2015-01-01', '2014-02-02', '2014-02-02');
insert into Orders values
(506, '2014-01-01', 300, '80808084', '2015-01-01', '2014-02-02', '2014-02-02');
insert into Orders values
(507, '2014-01-01', 300, '80808084', '2015-01-01', '2014-02-02', '2014-02-02');
insert into Orders values
(508, '2014-01-01', 300, '80808084', '2015-01-01', '2014-02-02', '2014-02-02');






insert into PurchaseItem values
(500, 100, 5);
insert into PurchaseItem values
(501, 102, 5);
insert into PurchaseItem values
(502, 103, 5);
insert into PurchaseItem values
(503, 105, 5);
insert into PurchaseItem values
(504, 106, 5);
insert into PurchaseItem values
(505, 106, 10);
insert into PurchaseItem values
(506, 106, 5);
insert into PurchaseItem values
(507, 107, 50);
insert into PurchaseItem values
(508, 106, 5);



insert into Returns values
(500, '2014-03-03', 500);

insert into ReturnItem values
(500, 100, 5);


commit;