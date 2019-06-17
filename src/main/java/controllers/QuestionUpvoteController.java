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

@WebServlet("/upvote-question")
public class QuestionUpvoteController extends HttpServlet {

    Connection connection;

    @Override
    public void init() throws ServletException {
        connection = DB.getConnection();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT  into question_voting (question_id,user_id,action) values (?,?,?)");

            HttpSession session = req.getSession();
            Integer userId = (Integer) session.getAttribute("userId");



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
