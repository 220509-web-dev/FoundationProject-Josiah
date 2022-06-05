# Reformats users record to produce username and split first and last names

import random

usernames = []

f1 = open("insert_records.sql", "r")
f2 = open("insert_users.sql","w")
f1.readline()
s = 'insert into users (username,fname,lname,address1,address2,city,state,postalcode) values\n'
f2.write(s)
s = f1.readline()
while (s):
  a = s.replace("(","").replace(");","").split(',')
  print(a)
  name = a[5].split("'")[1].split(" ")
  username = name[0][0]+name[1]+str(random.randint(1, 99))
  while (username in usernames):
    username = name[0][0]+name[1]+str(random.randint(1, 99))
  usernames.append(username)
  username += "@revature.net"
  if (len(name) in [0,1,3,4]):
    print("Flag",name,a)
  address1 = a[0].split("'")[1]
  record = "('"+username+"','"+name[0]+"','"+name[1]+"',"+",".join(a[0:5])+")"
  s = f1.readline()
  if (s):
    record += ",\n"
  f2.write(record)
f2.write(";")

f1.close()
f2.close()
