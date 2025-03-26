**Task Manager App**

A simple and secure task management web application built with **Java**, **Spring Boot**, **Thymeleaf** and **MySQL**. Users can register, log in, create and manage tasks and delete their accounts. Admins can view and manage non-admin users.

**Features**

- User registration and login
- Role-based access control (Admin/User)
- Add, update and delete tasks
- Secure password hashing using BCrypt
- Spring Security authentication and authorization
- Clean UI with Thymeleaf templates and CSS Bootstrap
- Test coverate (Controllers, Services, Security)

**Technologies Used**

- Java 23
- Spring Boot 3.4
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL
- JUnit / Mockito
- Maven


**Setup Instructions**

1. Clone the repository

   git clone https://github.com/ChestnutDots/task-manager-app.git
   cd task-manager-app

2. Configure your database
   - create a MySQL database named "task-directory"
   - update the application.properties fiel with your DB credentials

3. Run the app
   - with IntelliJ: Run the TaskManagerApplication.java file
   - or use ./mvnw spring-boot:run
  
4. Access the app
   - http://localhost:8080/
  
5. Run tests
   - ./mvnw test
  
**Folder Structure**

src/
├── main/
│   ├── java/
│   │   └── com/TaskManager/TaskManager/
│   │       ├── controller/
│   │       ├── dao/
│   │       ├── entity/
│   │       ├── security/
│   │       └── service/
│   └── resources/
│       ├── templates/
│       └── application.properties
└── test/

**Admin Access**

You can add admin roles manually to a user in the "roles" table (simply add ROLE_ADMIN).

**License**

This project is licensed under the MIT License.

**Author**

Linda Bistere

