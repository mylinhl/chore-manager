DROP DATABASE IF EXISTS chorerepository;
CREATE DATABASE chorerepository;
CREATE USER IF NOT EXISTS 'userChores'@'localhost'
IDENTIFIED BY 'userChoresPW';
GRANT CREATE, SELECT, INSERT, UPDATE, DELETE ON chorerepository.*
    TO 'userChores'@'localhost';
FLUSH PRIVILEGES;