CREATE DATABASE pizzadb;

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

INSERT TABLE state (name) VALUES ('PENDING');
INSERT TABLE state (name) VALUES ('SUCCESS');
INSERT TABLE state (name) VALUES ('CANCELLED');

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