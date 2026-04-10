DROP DATABASE IF EXISTS chorerepository;
CREATE DATABASE chorerepository;
CREATE USER IF NOT EXISTS 'userChores'@'localhost'
IDENTIFIED BY 'userChoresPW';
GRANT ALL PRIVILEGES ON chorerepository.*
    TO 'userChores'@'localhost';
FLUSH PRIVILEGES;