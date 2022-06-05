import names
import random_address
import string
import random
from datetime import datetime
 

loc = ['AR', 'CA', 'VT', 'OK', 'MD', 'FL', 'AL', 'CT', 'MA', 'AZ', 'TN', 'KY', 'GA', 'CO']
mx = len(loc)

sql1 = 'insert into users_private (user_id,social_sn,password) values \n'


def make_rec(n):  # Generate 
	ssn = random.randrange(10000000,99999999)
	pw = ''.join(random.choices(string.ascii_lowercase + string.ascii_uppercase + string.digits, k=20))
	return '('+str(n)+','+str(ssn)+",'"+pw+"')"



# Delete contents
sql_out = open('insert_records_private.sql','w')
sql_out.write('')
sql_out.close()

sql_out = open('insert_records_private.sql','a')
sql_out.write(sql1)

total = 50000
for i in range(total-1):
	if ( i%1000==0):
		now = datetime.now()
		print(i, now.strftime("%H:%M:%S"))
	sql_out.write(make_rec(i+1) + ',\n')

sql_out.write(make_rec(total) + ';')
sql_out.close()



