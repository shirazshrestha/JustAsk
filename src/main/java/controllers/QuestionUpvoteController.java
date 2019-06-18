package controllers;

import com.google.gson.Gson;
import entity.QuestionVote;

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
import java.util.HashMap;

@WebServlet("/question-upvote")
public class QuestionUpvoteController extends HttpServlet {

    Connection connection;

    @Override
    public void init() throws ServletException {
        connection = DB.getConnection();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String questionId = req.getParameter("id");
            String userId = req.getSession().getAttribute("userId").toString();
            Connection connection = DB.getConnection();
            System.out.println("user" + userId);
            PreparedStatement statement = connection.prepareStatement("select * from question_voting where question_id=? and user_id=?");
            statement.setString(1, questionId);
            statement.setString(2, userId);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                statement = connection.prepareStatement("insert into question_voting (question_id,user_id,action) values(?, ?, ?)");
                statement.setString(1, questionId);
                statement.setString(2, userId);
                statement.setString(3, "1");
                statement.executeUpdate();
            } else {
                QuestionVote vote = new QuestionVote();
                vote.setId(resultSet.getInt("id"));
                vote.setQuestionId(resultSet.getInt("question_id"));
                vote.setUserId(resultSet.getInt("user_id"));
                vote.setAction(resultSet.getInt("action"));

                if (vote.getAction() == 0) {
                    statement = connection.prepareStatement("update question_voting set action=? where id=?");
                    statement.setString(1, "1");
                    statement.setString(2, vote.getId().toString());
                    statement.executeUpdate();
                }
            }
            statement = connection.prepareStatement("select count(*) as count, action from question_voting where question_id=? group by action");
            statement.setString(1, questionId);
            resultSet = statement.executeQuery();
            HashMap<String, Integer> response = new HashMap<>();
            while (resultSet.next()) {
                response.put(resultSet.getInt("action") == 1 ? "upvotes" : "downvotes", resultSet.getInt("count"));
            }
            //GSON did not work
            //Gson gson = new Gson();
            //String json = gson.toJson(response);
            resp.setHeader("Content-type","application/json");
            Integer upvotes = response.get("upvotes");
            upvotes = upvotes == null ? 0 : upvotes;
            Integer downvotes = response.get("downvotes");
            downvotes = downvotes == null ? 0 : downvotes;
            resp.getWriter().write("{\"upvotes\":"+upvotes+",\"downvotes\":"+downvotes+"}");
        } catch (Exception e) {
            System.out.println("TestTest");
            System.out.println(e.getMessage());
        }

    }
}
