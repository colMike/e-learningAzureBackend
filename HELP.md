# Expenses-Monitor-API

REST API for tracking expenses.

A RESTful API created using Spring Boot. It uses Mysql as the relational database and JdbcTemplate to interact with that.
Apart from this,it uses JSON Web Token (JWT) to add authentication. Using JWT, we can protect certain endpoints and ensure that user must be logged-in to access those.


## Setup and Installation

1. **Clone the repo from GitHub**
   ```sh
   git clone https://github.com/colmike/expense-tracker-api.git
   cd expense-tracker-api
   ```
2. **Set up MySQL database**

   You can download mysql from the link below:
   - [here](https://www.mysql.org/download) and install it locally on the machine
   
3. **Create database objects**

   In the root application directory (expense-tracker-api), SQL script file (expensetracker.sql) is present for creating all database objects
   
   - run the script using mysql client:
     ```
     mysql -U mysql --file expensetracker_db.sql
     ```
4. **(Optional) Update database configurations in application.properties**
   
   If your database is hosted at some cloud platform or if you have modified the SQL script file with some different username and password, update the src/main/resources/application.properties file accordingly:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/expensetracker
   spring.datasource.username=expensetracker
   spring.datasource.password=password
   ```
5. **Run the spring boot application**
   ```sh
   ./mvnw spring-boot:run
   ```
   this runs at port 8080 and hence all endpoints can be accessed starting from http://localhost:8080
