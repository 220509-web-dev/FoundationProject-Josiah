#!/ban/bash

#cd Desktop
# Run in same DIR as mk_tbl.sql

# Build container
docker run --name local-db -p 5432:5432 -e POSTGRES_PASSWORD=revature -d postgres

# Copy mk_tbl.sql into DB
docker cp mk_tbl.sql local-db:/home

# Run mk_tbl.sql within DB
winpty docker exec -it local-db psql -U postgres -d postgres -c "\i /home/mk_tbl.sql;"

# Delete DB
docker stop local-db && docker rm local-db && docker ps -a
