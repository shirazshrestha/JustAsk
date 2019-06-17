package controllers;

import com.google.gson.Gson;
import entity.AnswerVote;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.HashMap;

@WebServlet("/answer-upvote")
public class AnswerUpVoteController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String answerId = req.getParameter("id");
            String userId = req.getSession().getAttribute("userId").toString();
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from answer_voting where answer_id=? and user_id=?");
            statement.setString(1, answerId);
            statement.setString(2, userId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                statement = connection.prepareStatement("insert into answer_voting (answer_id,user_id,action) values(?, ?, ?)");
                statement.setString(1, answerId);
                statement.setString(2, userId);
                statement.setString(3, "1");
                statement.executeUpdate();
            } else {
                AnswerVote vote = new AnswerVote();
                resultSet.next();
                vote.setId(resultSet.getInt("id"));
                vote.setAnswerId(resultSet.getInt("answer_id"));
                vote.setUserId(resultSet.getInt("user_id"));
                vote.setAction(resultSet.getInt("action"));

                if (vote.getAction() == 0) {
                    statement = connection.prepareStatement("update answer_voting set action=? where id=?");
                    statement.setString(1, "1");
                    statement.setString(2, vote.getId().toString());
                    statement.executeUpdate();
                }
            }
            statement = connection.prepareStatement("select count(*) as count, action from answer_voting where answer_id=? group by action");
            statement.setString(1, answerId);
            resultSet = statement.executeQuery();
            HashMap<String, Integer> response = new HashMap<>();
            if (resultSet.next()) {
                response.put(resultSet.getInt("action") == 1 ? "upvotes" : "downvotes", resultSet.getInt("count"));
                if (resultSet.next()) {
                    response.put(resultSet.getInt("action") == 1 ? "upvotes" : "downvotes", resultSet.getInt("count"));
                }
            }

            Gson gson = new Gson();
            String json = gson.toJson(response);
            resp.getWriter().write(json);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
