select i.upc, category, price, sum(p.quantity), sum(price)
from Item i, PurchaseItem p, Orders
where Orders.dates = '2014-01-01' and i.upc = p.upc and p.receiptId = Orders.receiptId
group by i.upc
order by category;