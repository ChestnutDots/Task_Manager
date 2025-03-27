# **Task Manager App**

A simple and secure task management web application built with **Java**, **Spring Boot**, **Thymeleaf** and **MySQL**. Users can register, log in, create and manage tasks and delete their accounts. Admins can view and manage non-admin users.

## **Features**

- User registration and login
- Role-based access control (Admin/User)
- Add, update and delete tasks
- Secure password hashing using BCrypt
- Spring Security authentication and authorization
- Clean UI with Thymeleaf templates and CSS Bootstrap
- Test coverate (Controllers, Services, Security)

## **Technologies Used**

- Java 23
- Spring Boot 3.4
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL
- JUnit / Mockito
- Maven

## **App Features and Demos**

1. Create a New User
![Task Manager Demo](GIFs/CreatingANewUser.gif)

2. Log in
![Task Manager Demo](GIFs/LoggingIn.gif)

3. Add a Task
![Task Manager Demo](GIFs/AddingATask.gif)

4. Update a Task
![Task Manager Demo](GIFs/UpdateTask.gif)

5. Delete A Task
![Task Manager Demo](GIFs/DeleteATask.gif)

6. Log Out
![Task Manager Demo](GIFs/LogOut.gif)

7. Delete Account
![Task Manager Demo](GIFs/DeleteAccount.gif)

8. Admin User Management
![Task Manager Demo](GIFs/AdminUserManagement.gif)  


## **Setup Instructions**

1. Clone the repository

   git clone https://github.com/ChestnutDots/Task_Manager.git
   cd task-manager-app

2. Configure your database
   - create a MySQL database named "task-directory" (you can use the schema.sql file in the sql_scripts folder)
   - update the application.properties fiel with your DB credentials

3. Configure the application.properties file
   - rename the sample-application.properties to application.properties
   - add your own database login information

4. Run the app
   - with IntelliJ: Run the TaskManagerApplication.java file
   - or use ./mvnw spring-boot:run
  
5. Access the app
   - http://localhost:8080/
  
6. Run tests
   - ./mvnw test
  
## **Folder Structure**

<pre>
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

</pre>

## **Admin Access**

You can add admin roles manually to a user in the "roles" table (simply add ROLE_ADMIN).

## **License**

This project is licensed under the MIT License.

## **Author**

Linda Bistere

