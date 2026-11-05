<html>
    <head>
        <title>MVC CRUD APPLICATION</title>
    </head>
    <body>
        <h2>MVC CRUD APPLICATION</h2>
        <h5>SERVLET + JSP + JDBC</h5>

        <h4>Student (Add/Edit) Form</h4>

        <form action="students?action=insert" method="post">
            <label>Name</lable>
            <input type="text" name="name" placeholder="Enter Name" required<br><br>

            <label>Email</lable>
            <input type="email" name="email" placeholder="Enter Email" required<br><br>

            <lable>Mobile</label>
            <input type="text" name="mobile" placeholder="Enter Mobile " required<br><br>

            <button type="submit">Save</button>
            <a href="students">Cancel</a>
        </form>
    </body>
</html>