# springSecurityDB

A Spring Boot application that demonstrates authentication and role-based authorization using Spring Security, JPA, and MySQL.

## Features

- Form-based login using Spring Security
- Public endpoints for registration and open access
- Role-based access (`ADMIN` protected endpoint)
- User data stored in MySQL via Spring Data JPA
- Password hashing with BCrypt

## Tech Stack

- Java 17
- Spring Boot 4.0.3
- Spring Security
- Spring Data JPA
- MySQL
- Maven

## Project Structure

- `src/main/java/org/hartford/springsecuritydb/SpringSecurityDbApplication.java` - app entry point and security config
- `src/main/java/org/hartford/springsecuritydb/controller/UserController.java` - REST endpoints
- `src/main/java/org/hartford/springsecuritydb/entity/User.java` - JPA entity mapped to `users`
- `src/main/java/org/hartford/springsecuritydb/repository/UserRepository.java` - user repository
- `src/main/java/org/hartford/springsecuritydb/service/CustomUserDetailsService.java` - user lookup for authentication
- `src/main/resources/application.properties` - datasource and JPA config

## Security Configuration (Current)

From `SpringSecurityDbApplication`:

- `GET/POST /public/**` -> permitted to everyone
- `/admin/**` -> requires role `ADMIN`
- Any other route -> authenticated user
- Login mechanism -> default Spring Security form login

Password handling:

- Passwords are encoded with `BCryptPasswordEncoder` before saving (`/public/register`).

## Endpoints

- `GET /public/hello` -> public message
- `POST /public/register` -> register a new user
- `GET /user/hello` -> requires authentication
- `GET /admin/hello` -> requires `ROLE_ADMIN`

### Register Request Example

```json
{
  "username": "alice",
  "password": "alice123",
  "roles": "USER"
}
```

For admin access, store roles including `ADMIN` (for example: `"ADMIN"` or `"USER,ADMIN"`).

## Prerequisites

- JDK 17+
- MySQL running locally
- Maven (or use the Maven Wrapper included in this repo)

## Database Setup

Current `application.properties` points to:

- URL: `jdbc:mysql://localhost:3306/security_db`
- Username: `root`
- Hibernate DDL: `update`

Create the database before running:

```sql
CREATE DATABASE security_db;
```

## Run the Application

Using Maven Wrapper on Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Or package and run:

```powershell
.\mvnw.cmd clean package
java -jar .\target\springSecurityDB-0.0.1-SNAPSHOT.jar
```

## How Authentication Works

1. A user is registered through `/public/register` with BCrypt-encoded password.
2. During login, Spring Security calls `CustomUserDetailsService`.
3. User is loaded from the `users` table by username.
4. `roles` are split by comma and converted to Spring authorities.

## Notes

- This project currently uses basic defaults and is intended as a learning/practice app.
- Consider moving sensitive DB credentials out of `application.properties` for real environments (for example, environment variables or profile-specific config).

