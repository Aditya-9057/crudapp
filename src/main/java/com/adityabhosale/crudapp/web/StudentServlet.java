package com.adityabhosale.crudapp.web;

import com.adityabhosale.crudapp.dao.StudentDAO;
import com.adityabhosale.crudapp.dao.StudentDAOimpl;
import com.adityabhosale.crudapp.exception.DAOException;
import com.adityabhosale.crudapp.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;


@WebServlet(urlPatterns = {"/","/students"})
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "add": showNewForm(req,resp);
                    break;
                case "delete": deleteStudent(req,resp);
                    break;
                case "update": updateStudent(req,resp);
                    break;
                case "edit": showEditForm(req,resp);
                    break;
                case "insert": insertStudent(req,resp);
                    break;
                default:
                    listStudents(req, resp); //case "List";
                    break;
            }

        } catch (DAOException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("errorCause", e.getCause());
            req.setAttribute("errorException", e);

            e.printStackTrace(); //developers logs

            req.getRequestDispatcher("error.jsp").forward(req,resp);
        }
    }

    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDAOimpl();
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("student_form.jsp").forward(req,resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Student student = studentDAO.getStudentById(id);
        req.setAttribute("student",student);

        req.getRequestDispatcher("student_form.jsp").forward(req,resp);


    }



    private void listStudents(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> studentList = studentDAO.getAllStudents();//use model
        req.setAttribute("students",studentList);//set data for view
        req.getRequestDispatcher("student_list.jsp").forward(req,resp);//call view
    }
    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));//receive data from view
        studentDAO.delete(id);//pass data to model
        resp.sendRedirect("students?action=list&success=Deleted Successfully");//call view with success/failure
    }

    private void insertStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        if (!validate(req,name,email,mobile)){
            req.getRequestDispatcher("student_form.jsp").forward(req,resp);
            return;

        }
        studentDAO.insert(new Student(name.trim(),email.trim(),mobile.trim()));
        resp.sendRedirect("students?action=list&success=Inserted Successfully");
    }

    private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");

        if (!validate(req,name,email,mobile)){
            req.setAttribute("student",new Student(id,name,email,mobile));
            req.getRequestDispatcher("student_form.jsp").forward(req,resp);
            return;

        }




        studentDAO.update(new Student(id,name.trim(),email.trim(),mobile.trim()));
        resp.sendRedirect("students?action=list&success=Updated Successfully");
    }

    private boolean validate(HttpServletRequest req,
                             String name, String email, String mobile) {
        boolean valid = true;

        if (name == null || !Pattern.matches("^[A-Za-z ]{3,50}$", name.trim())) {
            req.setAttribute("nameError", "Enter a name with 3–50 letters.");
            valid = false;
        }

        if (email == null || !Pattern.matches("^[A-Za-z0-9_.-]+@(.+)$", email.trim())) {
            req.setAttribute("emailError", "Enter a valid email address.");
            valid = false;
        }

        if (mobile == null || !Pattern.matches("^[0-9]{10}$", mobile.trim())) {
            req.setAttribute("mobileError", "Enter a 10-digit mobile number.");
            valid = false;
        }

        return valid;
    }
}
