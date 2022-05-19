-- SQL code that works
select 'address_billing' "Billing Address" from users; 
select u.address_billing from users u;
select u.address_billing "alias" from users u;
select u.address_billing "alias", up.social_sn "SSN" from users u, users_private up;
select u.full_name "Name", u.address_billing "Billing Address", up.social_sn "SSN" from users u 
inner join users_private up on u.user_id=up.user_id;