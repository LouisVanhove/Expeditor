DROP TABLE ARTICLES_ORDERS;
DROP TABLE ORDERS;
DROP TABLE ARTICLES;
DROP TABLE CLIENTS;
DROP TABLE STATES ;
DROP TABLE EMPLOYEES;
DROP TABLE PROFILES;

CREATE TABLE PROFILES(
	id INT PRIMARY KEY IDENTITY,
	label VARCHAR(100) NOT NULL
);

CREATE TABLE EMPLOYEES(
	id INT PRIMARY KEY IDENTITY,
	login VARCHAR(100) UNIQUE NOT NULL,
	password VARCHAR(250) NOT NULL,
	name VARCHAR(250) NOT NULL,
	firstname VARCHAR(250) NOT NULL,
	profile INT FOREIGN KEY REFERENCES PROFILES(id) NOT NULL, 
	archived BIT DEFAULT 0 NOT NULL
);

CREATE TABLE STATES(
	id INT PRIMARY KEY IDENTITY,
	label VARCHAR(100) NOT NULL
);

CREATE TABLE CLIENTS(
	id INT PRIMARY KEY IDENTITY,
	name VARCHAR(250) NOT NULL,
	address VARCHAR(250) NOT NULL,
	zip_code CHAR(5) NOT NULL,
	city VARCHAR(100) NOT NULL,
	archived BIT DEFAULT 0 NOT NULL
);

CREATE TABLE ARTICLES(
	id INT PRIMARY KEY IDENTITY,
	label VARCHAR(100) NOT NULL,
	description VARCHAR(250),
	weight INT NOT NULL,
	archived BIT DEFAULT 0 NOT NULL
);

CREATE TABLE ORDERS(
	id INT PRIMARY KEY IDENTITY,
	id_client INT FOREIGN KEY REFERENCES CLIENTS(id) NOT NULL,
	id_employee INT FOREIGN KEY REFERENCES EMPLOYEES(id),
	order_date DATE NOT NULL,
	treatment_date DATE,
	state INT FOREIGN KEY REFERENCES STATES(id),
	archived BIT DEFAULT 0 NOT NULL
);

CREATE TABLE ARTICLES_ORDERS(
	id_order INT FOREIGN KEY REFERENCES ORDERS(id), 
	id_article INT FOREIGN KEY REFERENCES ARTICLES(id), 
	quantity INT NOT NULL,
	PRIMARY KEY(id_order, id_article)
);