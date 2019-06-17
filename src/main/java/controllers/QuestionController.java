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

@WebServlet("/question")
public class QuestionController extends HttpServlet {

    Connection connection;

    public QuestionController() {
        connection = DB.getConnection();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String title = req.getParameter("questiontitle");
            String tags = req.getParameter("tags");

            //Session is required to get User Id
            HttpSession session = req.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            System.out.println("User: " + userId);

            PreparedStatement statement = connection.prepareStatement("insert into question (title,user_id) values (?,?)");
            statement.setString(1, title);
            statement.setString(2, userId.toString());
            statement.executeUpdate();

            statement = connection.prepareStatement("SELECT LAST_INSERT_ID() as id");
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                throw new Exception("Question was not added");
            }

            if (tags == null) {
                resp.sendRedirect(req.getContextPath() + "/feed");
                return;
            }
            String qid = result.getString("id");
            String[] tagArr = tags.split(",");
            for (String tag : tagArr) {
               statement = connection.prepareStatement("insert into question_tag (question_id,tag) values (?, ?)");
               statement.setString(1, qid);
               statement.setString(2, tag);
               statement.executeUpdate();
            }
            resp.sendRedirect(req.getContextPath() + "/feed");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
