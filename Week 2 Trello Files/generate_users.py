import names
import random_address
import string
import random
from datetime import datetime
 

loc = ['AR', 'CA', 'VT', 'OK', 'MD', 'FL', 'AL', 'CT', 'MA', 'AZ', 'TN', 'KY', 'GA', 'CO']
mx = len(loc)

sql1 = 'insert into "Users" ("Address1","Address2","City","State","PostalCode","Name") values\n'


def make_rec():  # Generate 
	txt = '('
	rand_ind = random.randrange(mx)
	state = loc[rand_ind]
	addr = random_address.real_random_address_by_state(state)
	nam = names.get_full_name()
	nam = nam.replace("'",'')
	#print(addr)
	for key_ in ['address1','address2','city','state','postalCode']:
		if (key_ in addr and addr[key_] != ''):
			txt += "'"+addr[key_].replace("'",'')+"',"
		else:
			txt += 'null,'
	txt += "'" +  nam + "')"
	return txt


# Delete contents
sql_out = open('insert_records.sql','w')
sql_out.write('')
sql_out.close()

sql_out = open('insert_records.sql','a')
sql_out.write(sql1)

for i in range(50000-1):
	if ( i%1000==0):
		now = datetime.now()
		print(i, now.strftime("%H:%M:%S"))
	sql_out.write(make_rec() + ',\n')

sql_out.write(make_rec() + ';')
sql_out.close()



