package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("")
public class IndexController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = DB.getConnection();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("select * from user");
            resultSet.next();
            String usernames = resultSet.getString("username");
            System.out.println(usernames);
            req.setAttribute("usernames",usernames);
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("login.jsp").forward(req,resp);

    }
}
