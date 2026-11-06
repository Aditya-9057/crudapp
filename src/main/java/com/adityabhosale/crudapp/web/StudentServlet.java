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
                case "add":
                    break;
                case "delete": deleteStudent(req,resp);
                    break;
                case "update":
                    break;
                case "edit":
                    break;
                case "insert":
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
}
