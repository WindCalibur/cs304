##Presentation:

## Remove the comments before you do the presentation so the TA don't see all this. Kthxbye

## Logging in and registering

# Show how entering wrong fields shows errors
# Show that registeration adds field onto customer by selecting customer before and after registeration
# Show that incorrect login gives error
# Login

select *
from Customer;

## Online orders:

# First show that only max quantity is inserted into the shopping cart by entering a super large quantity. While doing so, show that displayed 
# results of query is correct. Go back and change quantity and show it is updated
# First show the purchaseItem table, Order table, and Item table, and then do it again after purchase. Show that the latest entry (highest 
# receiptId) is added.

select *
from Item;

select *
from PurchaseItem;


## Returns

# Again, show that entering fields wrong gives error (try dates!)
# Try date that is too far away. Show a order table to see dates

# Check quantity and price before from PurchaseItem and Item and do a return. Verify that the result is expected.

# Show that returns are added to both return tables and quantity for item is updated

select *
from Orders;

select *
from Returns;

select *
from ReturnItem;

select *
from Item;

select *
from PurchaseItem;

## Show addItem, easy. Run select below twice, once before adding and once after. Check for errors before

select *
from Item;

## Daily Reports, again, easy. Try bad date, then show data. Add a purchase and check results again to verify its changed. Comment
# on how it is organized
# WARNING: Table is not flushed. Flush table by going back and clicking the daily report again. I said this as a design choice since
# I didn't have time to change it

insert into PurchaseItem values
(500, 103, 10);

## TopRowData, same concept as daily reports. Show item, and its upc, add a purchase, verify that quantity is updated

select *
from Item;

insert into PurchaseItem values
(501, 107, 10);

## Lastly, add delivery date. Add onto receiptId 500. Verify both before and after execution.

select *
from Orders;