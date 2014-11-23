# First see if return date and receiptId is valid

# command below is execute 2nd
insert into Returns values
(701, '2014-03-03', 500);
insert into ReturnItem values
(700, 100, 5);

#executed first
select count(*), upc, quantity
from PurchaseItem, Orders, Returns
where Orders.receiptId = PurchaseItem.receiptId and datediff(Returns.dates, Orders.dates) > 15 and Returns.receiptId = PurchaseItem.receiptId;

# Return price of item for refund calculation
(select price
from Returns, ReturnItem, Item
where ReturnItem.retId = Returns.retId and  ReturnItem.upc = Item.upc);