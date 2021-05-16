CREATE USER 'dolphinserver'@'localhost' IDENTIFIED BY 'dolphin';

CREATE SCHEMA DOLPHINDB;
GRANT ALL PRIVILEGES ON DOLPHINDB . * TO 'dolphinserver'@'localhost';
FLUSH PRIVILEGES;