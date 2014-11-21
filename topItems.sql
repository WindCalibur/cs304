use project3;

select title, company, stock, sum(quantity) 
from Orders, PurchaseItem, Item
where Orders.dates = '2014-01-01' and Orders.receiptId = PurchaseItem.receiptId and PurchaseItem.upc = Item.upc
group by Item.upc
order by quantity
desc limit 5;