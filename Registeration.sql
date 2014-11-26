## Check if upc exists

select count(*)
from Customer
where Customer.cid = 100;

# Insert

insert into Customer values
(300, 'Firebolt', 'Steve', '2222 Broadway', 6041234567);