# The Voprostion [vəˈprɔʃən]

A web application running on Spring Boot

## Technology stack
  - Java 8
  - Spring Data Jpa
  - Spring Security
  - Spring Boot
  - Spring Rest
  - Spring MVC
  - Thymeleaf

## Use cases
1. User authorization
2. Asking a question
3. Answering
4. Voting for an answer
5. Searching by tag/user

## Compile
This app uses the Lombok annotation processor, so you have to enable annotation processing in your IDE

## Run
1. You need to edit the following properties in the application.properties file:
   ```
   spring.jpa.hibernate.ddl-auto=create
   ```
   Using this property the app will create the DB schema.
2. Run the DB in-memory server
   ```sh
   java -cp h2-1.4.190.jar org.h2.tools.Server
   ```
3. Run AppApplication class from the Idea or using gradle command-line.
