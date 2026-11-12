# 🎓 Student CRUD Application

<p align="center">
  A Java web application for managing student records with CRUD operations, pagination, and PostgreSQL persistence.
</p>

<p align="center">
  <img alt="Java 17+" src="https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white">
  <img alt="Maven" src="https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white">
  <img alt="Jakarta Servlet 6.1" src="https://img.shields.io/badge/Jakarta_Servlet-6.1-2C7A7B">
  <img alt="Apache Tomcat 11" src="https://img.shields.io/badge/Server-Tomcat_11-F8DC75?logo=apachetomcat&logoColor=black">
  <img alt="PostgreSQL" src="https://img.shields.io/badge/Database-PostgreSQL-4169E1?logo=postgresql&logoColor=white">
</p>

---

## 📖 About

Student CRUD Application is a learning project that demonstrates a classic **Model–View–Controller (MVC)** structure with Java Servlets, JSP, JDBC, and PostgreSQL. The web interface lets users browse and manage student records, with server-side validation and paginated results.

## 📚 Contents

- [Features](#-features)
- [Technology](#-technology)
- [Requirements](#-requirements)
- [Getting started](#-getting-started)
- [Build and deploy](#-build-and-deploy)
- [Application routes](#-application-routes)
- [Project structure](#-project-structure)
- [Database schema](#-database-schema)
- [How it works](#-how-it-works)
- [Security and project notes](#-security-and-project-notes)

## ✨ Features

| Feature | Description |
| --- | --- |
| Student list | Displays records stored in PostgreSQL |
| Pagination | First, previous, next, last, and numbered page navigation |
| Page size | Choose 5, 10, 20, 50, 100, or 200 records per page |
| Add student | Create a record with name, email, and mobile number |
| Edit student | Load an existing record into the form and update it |
| Delete student | Remove a student record |
| Input validation | Server-side checks for name, email, and 10-digit mobile number |
| Feedback | Success messages and an application error page |

## 🧰 Technology

- **Java 17 or later**
- **Maven** for dependency management and WAR packaging
- **Jakarta Servlet API 6.1** and JSP
- **Apache Tomcat 11**
- **PostgreSQL** and PostgreSQL JDBC driver
- **Bootstrap 6 alpha** and Font Awesome for the UI

The Bootstrap and Font Awesome assets are loaded from CDNs, so the browser needs internet access to display those styles and icons.

## ✅ Requirements

Install the following before running the project:

1. JDK 17 or later
2. Apache Maven
3. PostgreSQL
4. Apache Tomcat 11

Tomcat 11 uses Jakarta Servlet APIs and requires Java 17 or later.

## 🚀 Getting started

### 1. Get the project

Clone the repository and open the project directory containing `pom.xml`:

```bash
git clone <YOUR_REPOSITORY_URL>
cd crudapp
```

Replace `<YOUR_REPOSITORY_URL>` with the HTTPS or SSH URL of your GitHub repository.

### 2. Create and initialize the database

The application expects a database named `cruddb` with a `student` table. The included `student.sql` script creates the database, table, and three sample records. Run it with `psql`:

```bash
psql -U postgres -f student.sql
```

> [!CAUTION]
> The script begins with `DROP DATABASE IF EXISTS cruddb`. Running it deletes and recreates the existing `cruddb` database, including its data. Back up any records you need before running it. The `\c` command in this script is a `psql` command, so run the script with `psql`, not by pasting the whole file into a GUI query window.

### 3. Configure the database connection

Update the JDBC URL, PostgreSQL username, and password in:

```text
src/main/java/com/adityabhosale/crudapp/utils/JDBCUtils.java
```

The current source uses `localhost:5433`. Set the port to match your PostgreSQL server; `5432` is the common default. The database name should match `cruddb`, unless you also change the SQL script and JDBC URL.

**Do not publish database passwords.** The current project has connection credentials in Java source. Before pushing a public repository, replace them with environment-based configuration, remove any credentials from tracked files and commit history, and rotate the password if it was real or reused.

### 4. Build the application

From the directory containing `pom.xml`, run:

```bash
mvn clean package
```

Maven creates the deployable WAR at:

```text
target/crudapp.war
```

### 5. Deploy to Tomcat

For a manual Tomcat deployment:

1. Stop Tomcat.
2. Copy `target/crudapp.war` into Tomcat's `webapps` directory.
3. Start Tomcat and wait for the application to deploy.
4. Open the app in a browser:

   ```text
   http://localhost:8080/crudapp/
   ```

The context path is based on the WAR filename. With `crudapp.war`, Tomcat deploys the application at `/crudapp`.

## 🧭 Application routes

The servlet is mapped to `/` and `/students`. Examples below assume the app is deployed as `/crudapp`.

| Action | Example URL | Behavior |
| --- | --- | --- |
| List students | `/crudapp/students` | Shows the first page |
| Change page | `/crudapp/students?page=2&pageSize=10` | Shows page 2 with 10 records per page |
| Choose page size | `/crudapp/students?pageSize=20` | Shows 20 records per page |
| Add form | `/crudapp/students?action=add` | Opens a blank student form |
| Edit form | `/crudapp/students?action=edit&id=1` | Loads student with ID 1 into the form |
| Insert | Form submits to `students?action=insert` | Validates and saves a new student |
| Update | Form submits to `students?action=update` | Validates and updates a student |
| Delete | `/crudapp/students?action=delete&id=1` | Deletes the selected student |

The list JSP is intended to be reached through the servlet so it receives the selected students and pagination details.

## 🗂️ Project structure

```text
crudapp/
├── pom.xml
├── student.sql
└── src/
    └── main/
        ├── java/com/adityabhosale/crudapp/
        │   ├── dao/
        │   │   ├── StudentDAO.java
        │   │   └── StudentDAOimpl.java
        │   ├── exception/
        │   │   └── DAOException.java
        │   ├── model/
        │   │   ├── Pagination.java
        │   │   └── Student.java
        │   ├── utils/
        │   │   └── JDBCUtils.java
        │   └── web/
        │       └── StudentServlet.java
        └── webapp/
            ├── WEB-INF/web.xml
            ├── error.jsp
            ├── student_form.jsp
            └── student_list.jsp
```

## 🧱 Database schema

The included script creates this table:

| Column | Type | Constraints |
| --- | --- | --- |
| `id` | `SERIAL` | Primary key, generated automatically |
| `name` | `VARCHAR(100)` | Required |
| `email` | `VARCHAR(50)` | Required and unique |
| `mobile` | `VARCHAR(10)` | Required |

## 🔄 How it works

```text
Browser → StudentServlet → StudentDAO / StudentDAOimpl → JDBCUtils → PostgreSQL
                    ↓
                JSP views → Browser
```

- **StudentServlet** reads the requested action, validates form input, and coordinates the response.
- **StudentDAO** defines database operations; **StudentDAOimpl** implements them with prepared SQL statements.
- **Pagination** stores the current page and page size and calculates the database offset.
- **JDBCUtils** creates PostgreSQL connections.
- **JSP views** render the student list, forms, and errors.

## 🔐 Security and project notes

This is an educational project. Before deploying it publicly or using real data:

- Move database credentials out of source code; remove secrets from Git history and rotate any exposed password.
- Change delete to use POST rather than a link/GET request. The current servlet dispatches data-changing actions through the shared GET handler.
- Add CSRF protection for form submissions.
- HTML-escape user-provided values before displaying them in JSP pages.
- Add a `LICENSE` file if you want to grant others permission to reuse the project. Without a license, others do not receive an open-source reuse grant by default.

---

<p align="center"><sub>Built as a Java Servlets, JSP, JDBC, and PostgreSQL learning project.</sub></p>
