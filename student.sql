DROP DATABASE  IF EXISTS cruddb;
CREATE DATABASE cruddb;

\c cruddb

DROP TABLE IF EXISTS student;
CREATE TABLE student(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    mobile VARCHAR(10) NOT NULL
);

INSERT INTO student(name,email,mobile) VALUES('Adi bhosale','adi@gmail.com','1234567890');
INSERT INTO student(name,email,mobile) VALUES('Adit bhosale','adit@gmail.com','1234567899');
INSERT INTO student(name,email,mobile) VALUES('Aditya bhosale','aditya@gmail.com','1234567898');

SELECT * FROM student;