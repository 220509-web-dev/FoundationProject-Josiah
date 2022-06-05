set search_path to user_data;

select * from users limit 5; 

select address1 from users limit 5; 

select * from users where lower(fname) like '%josiah%';    -- 3 records

select count(*) from users where address2 is null;        -- 42932 records

select * from users_private offset 3000 limit 5;

select u.user_id, u.fname, up.social_sn "SSN" 
from users u 
inner join users_private up 
on u.user_id=up.user_id
offset 40000 limit 5; 