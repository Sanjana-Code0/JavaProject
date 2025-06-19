@echo off
echo Updating database schema...

mysql -u root -p < schema.sql

echo Schema update completed!
pause 