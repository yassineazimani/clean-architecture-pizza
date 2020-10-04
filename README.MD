## Clean architecture : Pizza Automate

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

### Building project

First, you have to create two environment variables : 

**PASSWORD_DB :** This is the password of your database (Hibernate JPA) account.

**USER_DB :** This is the user identifiant of your database (Hibernate JPA) account.

To build the project :

```bash
./gradlew clean build
```

The admin account is the following :

ID : 109856
pw : password

#### Console 

To run the console application :

```bash
```

If you want to login as admin you have to write id and password as arguments.

Example :

```bash
    java -jar cli.jar 109856 password
```

#### Spring Boot

To run the Spring Boot application :

```bash
```

To use a public endpoint :

```bash
curl "http://localhost:8080/api/public/{endpoint}"
```

Example :

```bash
curl "http://localhost:8080/api/public/categories"
```

My spring boot application uses JWT authentication to login.

More informations : https://jwt.io/ 

To login as admin :

```bash
curl "http://localhost:8080/api/auth/login" \
  -X POST \
  -d "{ \"id\": \"109856\", \"password\": \"password\" }" \
  -H "Content-Type: application/json" 
```

To use an admin endpoint :

```bash
curl "http://localhost:8080/api/admin/{endpoint}" \
  -X POST \
  -d "{ \"name\": \"\" }" \
  -H "Authorization: Bearer {Bearer given by endpoint login}" \
  -H "Content-Type: application/json" 
```

Example :

```bash
curl "http://localhost:8080/api/admin/category" \
  -X POST \
  -d "{ \"name\": \"Sandwichs\" }" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MDEzMTU1NTQsImlhdCI6MTYwMTMxMzc1NCwic3ViIjoiMTA5ODU2IiwiaWQiOiIxMDk4NTYifQ.RhR37VwCpnOpQNRqYAuyCWAawqXF-WR2cxqZbnYFgHc9QzqoiZw0Gl_t_4WrnYAlUk4ku_Ta0Vd_1g453e1IYw" \
  -H "Content-Type: application/json" 
```

#### Swing

To run the swing application :

```bash
```

#### FX

You must have Java 14 JRE to run Java FX.

To run the FX application :

```bash
    cd clean-architecture-pizza-adapters-primaries-fx-customer/build/image/bin
    fx-customer
```

### Description project

This project was made to show hexagonal architecture power. It's a simple automate for customers who want to buy pizzas/drinks/desserts...
The customer goes to the automate (a touchscreen), then he chooses what he wants to eat (products/quantity). After the order, he can
pay this one by coins or by bank card. Finally, the automate gives the order to the customer. 

A admin can use the application to manage orders, products and look at statistics about orders...

![Example console](images/usecases.png)

### Hexagonal architecture

Thanks to the hexagonal architecture, we can focus only in the business logic of the application without knowing 
frameworks, libraries used. Indeed, what matter if it is MySQL or MongoDB or filesystem used, the business logic
will stay the same without edit it. The philosophy stays the same with "tools frontal" like Spring Boot Rest controller
or Java FX/Swing or console... If tomorrow, i want to switch Java Swing by Java FX without change the business logic or
the persistence layer, i can do it and easily ! This is the power of the hexagonal architecture.
Moreover, thanks to the hexagonal architecture, you can focus easily on TDD and BDD (DDD too, i didn't use it in this project).

Some avantages :

* The business logic is independent

* Maintenance and Evolutions are easy to do

* We can test without to worry about frameworks and infrastructures used

* Dependency inversion used

Some cons :

* Not practical for little application

Hexagonal architecture used by this project : 

![Example console](images/architecture-pizza.png)

### Where can i change adapters around the business logic in the user application ?

#### Console

#### Spring boot

#### Swing

#### FX

### Screenshoots

#### With FX

![Example FX](images/screenshoot-start-panel.jpg)

#### With Console

![Example console](images/example-console.jpg)

#### With REST Spring Boot

![Example console](images/example-spring-boot.jpg)

### Resources 

https://www.amazon.fr/Clean-Architecture-Robert-Martin-2016-12-10/dp/B01N2GDUQ9

https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html

https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/ 
