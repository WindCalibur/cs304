# First see if return date and receiptId is valid

# command below is execute 2nd
insert into Returns values
(701, '2014-03-03', 500);
insert into ReturnItem values
(700, 100, 5);

# get retId

select max(retId)
from returns;

#executed first
select count(*), upc, quantity
from PurchaseItem, Orders
where Orders.receiptId = PurchaseItem.receiptId and datediff('2014-03-03', Orders.dates) > 15 and 500 = PurchaseItem.receiptId;

# Return price of item for refund calculation
(select price
from Returns, ReturnItem, Item
where ReturnItem.retId = Returns.retId and  ReturnItem.upc = Item.upc);

update Item set stock = stock - 5
where upc = 100;

select *
from Item;