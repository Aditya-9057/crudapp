package com.adityabhosale.crudapp.web;

import com.adityabhosale.crudapp.dao.UserDAO;
import com.adityabhosale.crudapp.dao.UserDAOimpl;
import com.adityabhosale.crudapp.exception.DAOException;
import com.adityabhosale.crudapp.model.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet(urlPatterns = {"/","/auth"})
public class AuthServlet extends HttpServlet {
    private UserDAO userDAO;


    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOimpl();
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
       String action = req.getParameter("action");

       try {
           if (action == null) {
               action = "login";
           }
           switch (action) {
               case "register": showRegisterPage(req,resp);
                   break;
               case "doRegister": registerUser(req,resp);
                   break;
               case "doLogin": loginUser(req,resp);
                   break;
               case "logout": logoutUser(req,resp);
                   break;
               default: showLoginPage(req,resp);
           }
       }catch(DAOException e){
           req.setAttribute("error", e.getMessage());
           req.setAttribute("error", e.getCause());
           req.setAttribute("error", e);

           e.printStackTrace(); //developers logs

           req.getRequestDispatcher("error.jsp").forward(req,resp);
       }
    }

    private void logoutUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{

        req.getSession().invalidate();
        resp.sendRedirect("auth");
    }

    private void loginUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userDAO.login(username,password);
        if(user==null){
            req.setAttribute("error", "Username or password is incorrect");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
            return;

        }
        req.getSession().setAttribute("user",user);
        resp.sendRedirect("students");

    }

    private void registerUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{

        String name = req.getParameter("name");
        String mobile = req.getParameter("mobile");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //if failed then redirect to same Page
        if(userDAO.exists(username)){
            req.setAttribute("errorMessage", "Username Already Exists");
            req.getRequestDispatcher("register.jsp").forward(req,resp);
            return;
        }

        User user = new User(name,mobile,username,password);
        userDAO.register(user);
        resp.sendRedirect("auth?success=Register Successfully");


    }

    private void showLoginPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("login.jsp").forward(req,resp);

    }

    private void showRegisterPage(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req,resp);

    }



}
