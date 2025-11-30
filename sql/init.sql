CREATE DATABASE IF NOT EXISTS db_api_rest;
USE db_api_rest;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

INSERT INTO users (name, email) VALUES
('Mario', 'mario@gmail.com'),
('Susana', 'susana@gmail.com'),
('Noelia', 'noelia@gmail.com'),
('Horacio', 'horacio@gmail.com'),
('Daiana', 'daiana@gmail.com'),
('Jose', 'jose@gmail.com'),
('Roberto', 'roberto@gmail.com'),
('Maria', 'maria@gmail.com'),
('Nadia', 'nadia@gmail.com'),
('Cecilia', 'cecilia@gmail.com');