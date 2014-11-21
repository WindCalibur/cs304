# First see if return date and receiptId is valid

select count(*)
from PurchaseItem, Returns, Orders
where Orders.receiptId = PurchaseItem.receiptId and datediff(Returns.dates, Orders.dates) > 15 and Returns.receiptId = PurchaseItem.receiptId; 

# update