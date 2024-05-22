cd ../
docker-compose up -d
sleep 2
PGPASSWORD=lQh3aAsApBHGXceY psql -h localhost -p 6003 -U yvmOiJUG -d network-programming -f db/initial_db.sql
