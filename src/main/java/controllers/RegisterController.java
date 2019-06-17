package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    Connection connection;

    public RegisterController() {
        connection = DB.getConnection();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String firstname = req.getParameter("firstname");
            String lastname = req.getParameter("lastname");
            String email = req.getParameter("email");

            PreparedStatement statement = connection.prepareStatement("select * from user where username=? ");
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                throw new Exception("Username already taken ");
            }

            statement = connection.prepareStatement("select  * from user where email=?");
            statement.setString(1, email);

            if (result.next()) {
                throw new Exception("Email already taken");
            }
            statement = connection.prepareStatement("insert into user (first_name,last_name,email,password,username) values (?, ? ,?, ?,?)");
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setString(5, username);
            statement.executeUpdate();

            statement=connection.prepareStatement("SELECT LAST_INSERT_ID()as id");
            result=statement.executeQuery();
            result.next();
            //SELECT LAST_INSERT_ID();
            //start session

            Integer id = result.getInt("id");
            HttpSession session = req.getSession();
            session.setAttribute("userId", id);


            resp.sendRedirect(req.getContextPath() + "/feed");

        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}
