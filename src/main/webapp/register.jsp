<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Register | Student CRUD</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@6.0.0-alpha.1/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #eaf2ff, #f7f9fc);
            color: #1f2937;
        }

        .register-card {
            width: 100%;
            max-width: 480px;
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

        .btn-register {
            padding: 0.75rem;
            border-radius: 10px;
            font-weight: 600;
        }
    </style>
</head>

<body class="d-flex align-items-center justify-content-center p-3">
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<main class="card register-card">
    <div class="card-body p-4 p-md-5">
        <div class="brand-icon">S</div>

        <h1 class="h3 text-center fw-bold mb-2">Create an account</h1>
        <p class="text-secondary text-center mb-4">
            Register to manage student records
        </p>

        <% if (errorMessage != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= errorMessage %>
            </div>
        <% } %>

        <form action="<%= request.getContextPath() %>/auth?action=doRegister"
              method="post">

            <div class="mb-3">
                <label for="name" class="form-label fw-medium">Name</label>
                <input type="text"
                       class="form-control"
                       id="name"
                       name="name"
                       placeholder="Enter your name"
                       autocomplete="name"
                       required>
            </div>

            <div class="mb-3">
                <label for="mobile" class="form-label fw-medium">Mobile</label>
                <input type="tel"
                       class="form-control"
                       id="mobile"
                       name="mobile"
                       placeholder="10-digit mobile number"
                       pattern="[0-9]{10}"
                       maxlength="10"
                       autocomplete="tel"
                       required>
                <div class="form-text">Enter 10 digits.</div>
            </div>

            <div class="mb-3">
                <label for="username" class="form-label fw-medium">Username</label>
                <input type="text"
                       class="form-control"
                       id="username"
                       name="username"
                       placeholder="Choose a username"
                       autocomplete="username"
                       required>
            </div>

            <div class="mb-4">
                <label for="password" class="form-label fw-medium">Password</label>
                <input type="password"
                       class="form-control"
                       id="password"
                       name="password"
                       placeholder="Create a password"
                       autocomplete="new-password"
                       required>
            </div>

            <button type="submit" class="btn btn-primary btn-register w-100">
                Create account
            </button>
        </form>

        <p class="text-center text-secondary mt-4 mb-0">
            Already have an account?
            <a href="<%= request.getContextPath() %>/auth"
               class="fw-semibold text-decoration-none">
                Sign in
            </a>
        </p>
    </div>
</main>

</body>
</html>