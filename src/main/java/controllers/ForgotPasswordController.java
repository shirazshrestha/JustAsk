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

@WebServlet("/forgotpassword")
public class ForgotPasswordController extends HttpServlet {

    Connection connection;

    @Override
    public void init() throws ServletException {
        connection = DB.getConnection();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("forgotpassword.jsp").forward(req, resp);
        System.out.println("forgot password test: it comes here");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String newpassword = req.getParameter("newpassword");

            PreparedStatement statement = connection.prepareStatement("SELECT id, username FROM user WHERE username = ?");
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                throw new Exception("No user found with that username");
            }

            statement = connection.prepareStatement("UPDATE user SET password = ? WHERE username = ?");
            statement.setString(1, newpassword);
            statement.setString(2, username);
            statement.executeUpdate();


            //start session
            Integer id = result.getInt("id");
            HttpSession session = req.getSession();
            session.setAttribute("userId", id);


            resp.sendRedirect(req.getContextPath() + "/feed");

        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("forgotpassword.jsp").forward(req, resp);
        }
    }
}
