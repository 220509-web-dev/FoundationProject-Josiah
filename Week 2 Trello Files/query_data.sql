select * from "Users" limit 5; 

select "Address1" from "Users" limit 5; 

select * from "Users" where lower("Name") like '%josiah%';    -- 3 records

select count(*) from "Users" where "Address2" is null;        -- 42932 records

select * from "Users_private" offset 3000 limit 5;

select u."user_id", u."Name", up.social_sn "SSN" 
from "Users" u 
inner join "Users_private" up 
on u.user_id=up.user_id
offset 40000 limit 5; 