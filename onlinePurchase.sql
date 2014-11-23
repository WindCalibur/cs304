# Check if customer exists

select count(*)
From Customer
Where Customer.cid = 300 and password = 'Firebolt';

# Register customer

insert into Customer values
(300, 'Firebolt', 'Steve', '2222 Broadway', 6041234567);

# Quantity check

select stock
from Item
Where Item.upc = 100;

# Update stock

update Item set stock = 30
where upc = 100;

# get max receiptId

select max(receiptId)
from Orders;

# Add order

insert into Orders values
(500, '2014-01-01', 300, '80808080', '2015-01-01', '2014-02-02', '2014-02-02');

# Add Purchase

insert into PurchaseItem values
(500, 100, 5);