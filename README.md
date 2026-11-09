# 🎓 Student CRUD Application

<p align="center">
  A Java web app for managing student records with Servlets, JSP, JDBC, and PostgreSQL.
</p>

<p align="center">
  <img alt="Java 17+" src="https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white">
  <img alt="Maven" src="https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white">
  <img alt="Jakarta Servlet 6.1" src="https://img.shields.io/badge/Jakarta_Servlet-6.1-2C7A7B">
  <img alt="Tomcat 11" src="https://img.shields.io/badge/Server-Tomcat_11-F8DC75?logo=apachetomcat&logoColor=black">
  <img alt="PostgreSQL" src="https://img.shields.io/badge/Database-PostgreSQL-4169E1?logo=postgresql&logoColor=white">
</p>

---

## ✨ Overview

A learning project demonstrating a classic **Model–View–Controller (MVC)** structure in Java. Users can view, add, edit, and delete student records through a simple web interface.

## 📋 Table of contents

- [Features](#-features)
- [Technology](#-technology)
- [Requirements](#-requirements)
- [Get started](#-get-started)
- [Build and deploy](#-build-and-deploy)
- [Project structure](#-project-structure)
- [Database table](#-database-table)
- [Application flow](#-application-flow)
- [Production notes](#-production-notes)

## ✨ Features

| Capability | Description |
| --- | --- |
| 📃 List | View student records from PostgreSQL |
| ➕ Create | Add students through a JSP form |
| ✏️ Update | Edit an existing student |
| 🗑️ Delete | Remove a student record |
| ✅ Validation | Check name, email, and mobile inputs |
| ⚠️ Error handling | Pass DAO errors to an application error page |

## 🧰 Technology

- **Java 17 or later**
- **Maven** for building the WAR
- **Jakarta Servlet 6.1** and JSP
- **Apache Tomcat 11**
- **PostgreSQL** with the PostgreSQL JDBC driver
- **Bootstrap 6 alpha** for page styling

## ✅ Requirements

Install a JDK, Maven, PostgreSQL, and Apache Tomcat 11. Tomcat 11 requires Java 17 or later.

## 🚀 Get started

### 1. Set up the database

Create a PostgreSQL database named `cruddb` and the `student` table, or use the included `student.sql` setup script to create the database, table, and sample rows.

> [!CAUTION]
> `student.sql` begins by dropping the existing `cruddb` database. Running it erases that database and its data. Back up any data you need first.

### 2. Configure the connection

Set the JDBC URL, username, and password in `JDBCUtils.java` to match your PostgreSQL installation. PostgreSQL commonly uses port `5432`; use the port configured on your machine.

**Keep credentials private.** The current project stores connection settings in source code. Replace them with environment-based configuration before publishing real credentials or deploying publicly. Never commit passwords to GitHub.

## 📦 Build and deploy

From the project root, build the WAR:

```bash
mvn clean package
```

The build creates:

```text
target/crudapp.war
```

Deploy `target/crudapp.war` to Tomcat:

1. Stop Tomcat.
2. Copy the WAR into Tomcat's `webapps` directory.
3. Start Tomcat and wait for deployment to finish.
4. Open the application:

   ```text
   http://localhost:8080/crudapp/
   ```

The preferred entry point is the servlet, which loads the records before forwarding to the JSP.

## 🗂️ Project structure

```text
src/
└── main/
    ├── java/com/adityabhosale/crudapp/
    │   ├── dao/          # Student data access interface and JDBC implementation
    │   ├── exception/    # DAO exception type
    │   ├── model/        # Student model
    │   ├── utils/        # JDBC connection utility
    │   └── web/          # Student servlet/controller
    └── webapp/
        ├── WEB-INF/      # Web application configuration
        ├── error.jsp
        ├── student_form.jsp
        └── student_list.jsp
student.sql              # Database and sample-data setup script
pom.xml                  # Maven dependencies and WAR packaging
```

## 🧱 Database table

The setup script creates a `student` table:

| Column | Type | Description |
| --- | --- | --- |
| `id` | `SERIAL PRIMARY KEY` | Student identifier |
| `name` | `VARCHAR(100)` | Student name |
| `email` | `VARCHAR(50) UNIQUE` | Student email address |
| `mobile` | `VARCHAR(10)` | Student mobile number |

## 🔄 Application flow

```text
Browser → StudentServlet → StudentDAO → JDBCUtils → PostgreSQL
                    ↓
                 JSP views → Browser
```

The servlet handles requests and selects an action. The DAO performs database operations through JDBC. The servlet then forwards request data to a JSP or redirects the browser back to the student list.

## 🔒 Production notes

This is a learning project. Before public deployment:

- Move database credentials out of source code.
- Use POST requests for data-changing actions such as delete.
- HTML-escape user-provided values before displaying them.
- Add appropriate protections such as CSRF defenses.

---

<p align="center"><sub>Built as a Java Servlet, JSP, JDBC, and PostgreSQL learning project.</sub></p>

