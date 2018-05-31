# The Voprostion [vəˈprɔʃən]

A web application running on Spring Boot

## Technology stack
  - Java 8
  - Spring Data Jpa
  - Spring Security
  - Spring Boot
  - Spring MVC
  - Thymeleaf

## Use cases
1. User authorization
2. Asking a question
3. Answering
4. Voting for an answer
5. Searching by tag/user

## Run
1. You need to edit the following properties in the application.properties file:
   ```
   spring.jpa.hibernate.ddl-auto=create
   ...
   app.filldb=true
   ```
   Using this properties the app will create the schema and populate DB with the simple user role and a moderator account.
2. Run the DB in-memory server
   ```sh
   java -cp h2-1.4.190.jar org.h2.tools.Server
   ```
3. Run AppApplication class from the Idea or using gradle command-line.
4. Before you restart the app make sure if the edited properties were restored
