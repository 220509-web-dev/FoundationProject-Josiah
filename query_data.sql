select u.address_billing 'billing address', 
u.full_name 'Full Name',
up.social_sn 'SSN', 
up.password 'password' 
from users u inner join users_private up on u.user_id = up.user_id;