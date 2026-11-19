<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login | Student CRUD</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@6.0.0-alpha.1/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #eaf2ff, #f7f9fc);
            color: #1f2937;
        }

        .login-card {
            width: 100%;
            max-width: 430px;
            border: 0;
            border-radius: 18px;
            box-shadow: 0 18px 50px rgba(31, 41, 55, 0.12);
        }

        .brand-icon {
            width: 58px;
            height: 58px;
            display: grid;
            place-items: center;
            margin: 0 auto 1rem;
            border-radius: 16px;
            background: #e8efff;
            color: #315cce;
            font-size: 1.6rem;
            font-weight: 700;
        }

        .form-control {
            padding: 0.75rem 0.9rem;
            border-radius: 10px;
        }

        .btn-login {
            padding: 0.75rem;
            border-radius: 10px;
            font-weight: 600;
        }
    </style>
</head>

<body class="d-flex align-items-center justify-content-center p-3">
<%
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
%>

<main class="card login-card">
    <div class="card-body p-4 p-md-5">
        <div class="brand-icon">S</div>

        <h1 class="h3 text-center fw-bold mb-2">Welcome back</h1>
        <p class="text-secondary text-center mb-4">
            Sign in to manage student records
        </p>

        <% if (success != null) { %>
            <div class="alert alert-success" role="status"><%= success %></div>
        <% } %>

        <% if (error != null) { %>
            <div class="alert alert-danger" role="alert"><%= error %></div>
        <% } %>

        <form action="<%= request.getContextPath() %>/auth?action=doLogin"
              method="post">

            <div class="mb-3">
                <label for="username" class="form-label fw-medium">Username</label>
                <input type="text"
                       class="form-control"
                       id="username"
                       name="username"
                       placeholder="Enter your username"
                       autocomplete="username"
                       required>
            </div>

            <div class="mb-4">
                <label for="password" class="form-label fw-medium">Password</label>
                <input type="password"
                       class="form-control"
                       id="password"
                       name="password"
                       placeholder="Enter your password"
                       autocomplete="current-password"
                       required>
            </div>

            <button type="submit" class="btn btn-primary btn-login w-100">
                Sign in
            </button>
        </form>

        <p class="text-center text-secondary mt-4 mb-0">
            Don’t have an account?
            <a href="<%= request.getContextPath() %>/auth?action=register"
               class="fw-semibold text-decoration-none">
                Register
            </a>
        </p>
    </div>
</main>

</body>
</html>