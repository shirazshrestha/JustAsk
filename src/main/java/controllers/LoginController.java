package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    Connection connection;

    public LoginController() {
        connection = DB.getConnection();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String remember = req.getParameter("remember");
            PreparedStatement statement = connection.prepareStatement("select * from user where username=? and password=?");
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                throw new Exception("User does not exist.");
            }
            //start session
            Integer id = result.getInt("id");
            HttpSession session = req.getSession();
            session.setAttribute("userId", id);

            if (remember != null) {
                Cookie cookie = new Cookie("user", id.toString());
                cookie.setMaxAge(86400 * 30);
                resp.addCookie(cookie);
            }
            resp.sendRedirect(req.getContextPath() + "/feed");
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
