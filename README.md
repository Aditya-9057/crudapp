# 🎓 Student Management System

A Java web application for managing student records. Users can register and log in, then add, view, edit, and delete students through a paginated interface.

<p align="center">
  <img alt="Java 17+" src="https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white">
  <img alt="Maven" src="https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white">
  <img alt="Jakarta Servlet 6.1" src="https://img.shields.io/badge/Jakarta_Servlet-6.1-2C7A7B">
  <img alt="Apache Tomcat 11" src="https://img.shields.io/badge/Server-Tomcat_11-F8DC75?logo=apachetomcat&logoColor=black">
  <img alt="PostgreSQL" src="https://img.shields.io/badge/Database-PostgreSQL-4169E1?logo=postgresql&logoColor=white">
</p>

---

## ✨ Features

- Account registration, login, and logout
- Session check for requests to the student servlet
- Create, view, update, and delete student records
- Paginated student list with selectable page sizes
- Server-side validation for student name, email, and mobile number
- PostgreSQL persistence using JDBC prepared statements
- JSP pages for login, registration, student forms, and student list
- Success messages and an error page

## 🧰 Technology

- Java 17 or later
- Maven
- Jakarta Servlet API 6.1 and JSP
- Apache Tomcat 11
- PostgreSQL
- Bootstrap 6 alpha and Font Awesome, loaded from CDNs

The browser needs an internet connection to load the CDN-hosted styles and icons.

## ✅ Requirements

Install:

1. JDK 17 or later
2. Apache Maven
3. PostgreSQL
4. Apache Tomcat 11

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone <YOUR_REPOSITORY_URL>
cd crudapp
```

Replace `<YOUR_REPOSITORY_URL>` with your GitHub repository URL.

### 2. Set up the database

The included `student.sql` script creates the `cruddb` database, student and user tables, and sample student records.

Run it from the project root:

```bash
psql -U postgres -f student.sql
```

> [!CAUTION]
> The script starts with `DROP DATABASE IF EXISTS cruddb`. Running it deletes and recreates `cruddb`, including its existing data. Back up any data you need before running it. The script uses `\c`, a `psql` command, so run it with `psql`.

### 3. Configure the database connection

Update the JDBC URL, username, and password in:

```text
src/main/java/com/adityabhosale/crudapp/utils/JDBCUtils.java
```

The current source uses PostgreSQL on port `5433`. Change it to match your local PostgreSQL configuration; `5432` is the common default.

### 4. Build the application

From the folder containing `pom.xml`, run:

```bash
mvn clean package
```

Maven creates the WAR file here:

```text
target/crudapp.war
```

### 5. Deploy to Tomcat

1. Stop Tomcat.
2. Copy `target/crudapp.war` into Tomcat’s `webapps` directory.
3. Start Tomcat and wait for deployment to finish.
4. Open the login page:

   ```text
   http://localhost:8080/crudapp/auth
   ```

Tomcat uses the WAR filename as the application context. With `crudapp.war`, the context path is `/crudapp`.

## 🧭 Application Routes

The routes below assume the app is deployed at `/crudapp`.

| Action | Route | Method |
| --- | --- | --- |
| Login page | `/crudapp/auth` | GET |
| Registration page | `/crudapp/auth?action=register` | GET |
| Submit login | `/crudapp/auth?action=doLogin` | POST |
| Submit registration | `/crudapp/auth?action=doRegister` | POST |
| Log out | `/crudapp/auth?action=logout` | GET |
| List students | `/crudapp/students` | GET |
| Select a page | `/crudapp/students?page=2&pageSize=10` | GET |
| Open add form | `/crudapp/students?action=add` | GET |
| Open edit form | `/crudapp/students?action=edit&id=1` | GET |
| Insert student | `/crudapp/students?action=insert` | POST |
| Update student | `/crudapp/students?action=update` | POST |
| Delete student | `/crudapp/students?action=delete&id=1` | GET |

The student servlet is intended to be used after login. The `AuthFilter` checks for a logged-in session on `/students`.

## 📄 Pagination

The student list defaults to page 1 with 10 records per page. Available page sizes are:

```text
5, 10, 20, 50, 100, 200
```

Pagination uses PostgreSQL `LIMIT` and `OFFSET` queries and provides first, previous, numbered, next, and last page navigation.

## 🗂️ Project Structure

```text
crudapp/
├── pom.xml
├── student.sql
└── src/
    └── main/
        ├── java/com/adityabhosale/crudapp/
        │   ├── dao/
        │   │   ├── StudentDAO.java
        │   │   ├── StudentDAOimpl.java
        │   │   ├── UserDAO.java
        │   │   └── UserDAOimpl.java
        │   ├── exception/
        │   │   └── DAOException.java
        │   ├── filter/
        │   │   └── AuthFilter.java
        │   ├── model/
        │   │   ├── Pagination.java
        │   │   ├── Student.java
        │   │   └── User.java
        │   ├── utils/
        │   │   └── JDBCUtils.java
        │   └── web/
        │       ├── AuthServlet.java
        │       └── StudentServlet.java
        └── webapp/
            ├── WEB-INF/web.xml
            ├── error.jsp
            ├── header.jsp
            ├── login.jsp
            ├── register.jsp
            ├── student_form.jsp
            └── student_list.jsp
```

## 🧱 Database Tables

### `student`

| Column | Type | Constraints |
| --- | --- | --- |
| `id` | `SERIAL` | Primary key |
| `name` | `VARCHAR(100)` | Required |
| `email` | `VARCHAR(50)` | Required, unique |
| `mobile` | `VARCHAR(10)` | Required |

### `users`

| Column | Type | Constraints |
| --- | --- | --- |
| `id` | `SERIAL` | Primary key |
| `name` | `VARCHAR(50)` | Required |
| `mobile` | `VARCHAR(10)` | Required |
| `username` | `VARCHAR(50)` | Required, unique |
| `password` | `VARCHAR(255)` | Required |

## 🔄 How It Works

```text
Browser → AuthServlet → UserDAO → PostgreSQL
                  ↓
                Session
                  ↓
Browser → AuthFilter → StudentServlet → StudentDAO → PostgreSQL
                                      ↓
                                   JSP views
```

- `AuthServlet` handles account registration, login, and logout.
- `AuthFilter` checks for a logged-in session before allowing access to `/students`.
- `StudentServlet` handles student actions, input validation, and pagination.
- DAO classes execute database operations using JDBC prepared statements.
- JSP pages display the forms, student list, and error messages.

## 🔐 Security Notes

This is a learning project and is **not ready for production use**. In the current implementation:

- Account passwords are stored and compared as plain text.
- Database credentials are stored in `JDBCUtils.java`.
- Student deletion and logout use GET requests.
- The auth filter protects `/students`; JSP pages are currently outside `WEB-INF`.

Before using real accounts or publishing for public use:

- Hash passwords with a suitable password-hashing algorithm.
- Move database credentials to environment-based configuration.
- Remove exposed credentials from Git history and rotate them if they were real or reused.
- Use POST for state-changing actions and add CSRF protection.
- Keep protected JSP views under `WEB-INF` and forward to them through servlets.
- HTML-escape user-provided values before displaying them.
- Add a `LICENSE` file if you want to grant others permission to reuse the project.

---

<p align="center">
  Built as a Java Servlets, JSP, JDBC, and PostgreSQL learning project.
</p>