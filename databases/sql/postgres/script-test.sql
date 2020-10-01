CREATE DATABASE pizzadbtest;

CREATE TABLE category(
  id SERIAL NOT NULL,
  name VARCHAR(40) NOT NULL,
  CONSTRAINT PK_category PRIMARY KEY(id)
);

CREATE TABLE product(
    id SERIAL NOT NULL,
    name VARCHAR(40) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL NOT NULL,
    quantityAvailable INTEGER NOT NULL,
    categoryId INTEGER NOT NULL,
    CONSTRAINT FK_category FOREIGN KEY (categoryId) REFERENCES category,
    CONSTRAINT PK_product PRIMARY KEY(id)
);

CREATE TABLE state(
    id SERIAL NOT NULL,
    name VARCHAR(40) NOT NULL,
    CONSTRAINT PK_state PRIMARY KEY(id)
);

INSERT INTO state (name) VALUES ('PENDING');
INSERT INTO state (name) VALUES ('SUCCESS');
INSERT INTO state (name) VALUES ('CANCELLED');

CREATE TABLE ordercmd(
    id SERIAL NOT NULL,
    orderDate TIMESTAMP NOT NULL,
    orderStateId INTEGER NOT NULL,
    total DECIMAL NOT NULL,
    transactionCBId VARCHAR(255) NULL,
    CONSTRAINT FK_state FOREIGN KEY (orderStateId) REFERENCES state,
    CONSTRAINT PK_order PRIMARY KEY(id)
);

CREATE TABLE order_has_products(
    orderId INTEGER NOT NULL,
    productId INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    CONSTRAINT FK_order FOREIGN KEY (orderId) REFERENCES ordercmd,
    CONSTRAINT FK_product FOREIGN KEY (productId) REFERENCES product,
    CONSTRAINT PK_order_has_products PRIMARY KEY (orderId, productId)
);

CREATE TABLE admin(
    id SERIAL NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT PK_admin PRIMARY KEY(id)
);

INSERT INTO admin (id, password) VALUES (109856, '$2a$12$boWhkrsHVdNZhQIgQCq/sum1IV.Wp1SIb9rJMxsAue6TkT5Fge9je');

INSERT INTO category(name) VALUES ('pizzas');
INSERT INTO category(name) VALUES ('drinks');
INSERT INTO category(name) VALUES ('desserts');

INSERT INTO product(name, description, price, quantityavailable, categoryid)
	VALUES ('Four-cheese pizza', 'Pizza with four cheeses', 8.90, 20, 1);

INSERT INTO product(name, description, price, quantityavailable, categoryid)
    VALUES ('Vegetarian pizza', 'Pizza with mushrooms, tomatos, peppers, carrot, onion', 7.90, 10, 1);

INSERT INTO product(name, description, price, quantityavailable, categoryid)
    VALUES ('Fish pizza', 'Pizza with salmon', 8.20, 18, 1);

INSERT INTO product(name, description, price, quantityavailable, categoryid)
    VALUES ('Pepperoni pizza', 'Delicious Pepperoni pizza', 8.20, 2, 1);

INSERT INTO product(name, description, price, quantityavailable, categoryid)
    VALUES ('Orangina', 'Orange drink with gas', 2.90, 4, 2);

INSERT INTO product(name, description, price, quantityavailable, categoryid)
    VALUES ('Coca-cola', 'The famous Coca-cola', 2.90, 60, 2);

INSERT INTO product(name, description, price, quantityavailable, categoryid)
    VALUES ('Lemon pie', 'Lemon pie', 4.90, 30, 3);

INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (1, '22/09/2020 20:16', 1, 35.6, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (2, '22/09/2020 20:40', 1, 8.9, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (3, '22/09/2020 20:41', 1, 8.9, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (4, '22/09/2020 20:42', 1, 17.8, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (5, '22/09/2020 20:46', 1, 17.8, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (6, '22/09/2020 20:48', 1, 17.8, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (7, '22/09/2020 20:50', 2, 8.9, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (8, '22/09/2020 20:52', 1, 8.9, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (9, '22/09/2020 21:02', 1, 8.9, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (10, '22/09/2020 21:03', 2, 8.9, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (11, '22/09/2020 21:05', 1, 8.9, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (12, '22/09/2020 21:07', 1, 26.7, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (13, '22/09/2020 21:18', 1, 17.8, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (14, '22/09/2020 21:25', 1, 26.7, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (15, '22/09/2020 21:28', 1, 8.9, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (16, '22/09/2020 21:31', 1, 8.9, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (17, '22/09/2020 21:34', 1, 26.7, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (18, '22/09/2020 21:35', 2, 17.8, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (19, '22/09/2020 21:37', 2, 17.8, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (20, '22/09/2020 21:38', 1, 8.9, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (21, '22/09/2020 21:44', 1, 8.9, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (22, '22/09/2020 21:47', 1, 17.8, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (23, '22/09/2020 21:53', 2, 44.3, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (24, '22/09/2020 21:56', 2, 16.8, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (25, '22/09/2020 21:59', 2, 8.9, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (26, '22/09/2020 21:59', 2, 43.8, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (27, '22/09/2020 22:10', 2, 56.3, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (28, '22/09/2020 22:23', 1, 17.8, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (29, '22/09/2020 22:24', 1, 17.8, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (30, '22/09/2020 22:26', 2, 86, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (31, '22/09/2020 22:28', 1, 26.7, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (32, '22/09/2020 22:30', 2, 50.1, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (33, '22/09/2020 22:35', 2, 42.8, 'iQH0UzRoLDdLn06');
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (34, '23/09/2020 20:00', 2, 44.5, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (35, '23/09/2020 20:01', 2, 11.1, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (36, '26/09/2020 16:07', 2, 8.9, NULL);
INSERT INTO ordercmd(
	id, orderdate, orderstateid, total, transactioncbid)
	VALUES (37, '26/09/2020 16:09', 2, 8.2, NULL);

INSERT INTO order_has_products(
	orderid, productid, quantity)
	VALUES (27, 1, 1);

INSERT INTO order_has_products(
	orderid, productid, quantity)
	VALUES (32, 1, 4);
INSERT INTO order_has_products(
	orderid, productid, quantity)
	VALUES (32, 6, 5);

INSERT INTO order_has_products(
	orderid, productid, quantity)
	VALUES (33, 1, 3);
INSERT INTO order_has_products(
	orderid, productid, quantity)
	VALUES (33, 2, 1);
INSERT INTO order_has_products(
	orderid, productid, quantity)
	VALUES (33, 4, 1);

INSERT INTO order_has_products(
	orderid, productid, quantity)
	VALUES (34, 1, 1);

INSERT INTO order_has_products(
	orderid, productid, quantity)
	VALUES (35, 3, 1);
INSERT INTO order_has_products(
	orderid, productid, quantity)
	VALUES (35, 5, 1);

INSERT INTO order_has_products(
	orderid, productid, quantity)
	VALUES (36, 1, 1);

INSERT INTO order_has_products(
	orderid, productid, quantity)
	VALUES (37, 3, 1);