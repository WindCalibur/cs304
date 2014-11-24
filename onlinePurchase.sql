# Check if customer exists

select count(*)
From Customer
Where Customer.cid = 300 and password = 'Firebolt';

# Register customer

insert into Customer values
(300, 'Firebolt', 'Steve', '2222 Broadway', 6041234567);

# Quantity check

select stock, count(*)
from Item
Where Item.upc = 100;

# Update stock

update Item set stock = stock - 30
where upc = 100;

# get max receiptId

select max(receiptId)
from Orders;

# Add order

insert into Orders values
(600, curdate(), 300, '80808080', '2015-01-01', '2014-02-02', date_add(curdate(), interval 7 day));

# check how many undelivered orders
select count(*) from Orders where deliveredDate IS NULL;

# Add Purchase

insert into PurchaseItem values
(500, 100, 5);

# Display table

select count(*)
from Item, LeadSinger, HasSong h
where Item.upc = LeadSinger.upc and Item.upc = h.upc and  category = 'rock' and name = 'Kristen Bell' and h.title = 'title';

select Item.upc, Item.title, type, category, company, year, price, stock, name, h.title AS htitle
from Item, LeadSinger, HasSong h
where category = 'rock' and name = 'Kristen Bell' and h.title = 'title' and Item.upc = LeadSinger.upc and Item.upc = h.upc;