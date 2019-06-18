package controllers;

import entity.Answer;
import entity.Feed;
import entity.Question;
import entity.User;
import services.AnswerService;
import services.FeedService;

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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/answer")
public class AnswerController extends HttpServlet {
    Connection connection;
    AnswerService service;

    public AnswerController() {
        connection = DB.getConnection();
        service = new AnswerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //Get All Tags
            req.setAttribute("tags", service.getAllTags());

            //get the question_id from url
            String id = req.getParameter("id");
            System.out.println(id);

            //get question from database
            PreparedStatement statement = connection.prepareStatement("select " +
                    "q.*, a.content as answer, a.user as answerUser, concat(u.first_name,' ',u.last_name) as questionUser," +
                    "t.tags, upvote.count as upvotes, downvote.count as downvotes " +
                    "from question q " +
                    "left join ( " +
                    "SELECT a.*,concat(u.first_name,' ',u.last_name) as user  " +
                    "FROM answer a " +
                    "    inner join user u on a.user_id = u.id " +
                    "WHERE a.id = ( " +
                    "SELECT max(id) " +
                    "FROM answer " +
                    "WHERE a.question_id = question_id " +
                    ") " +
                    ") a on a.question_id = q.id " +
                    "inner join user u on u.id = q.user_id " +
                    "left join (select group_concat(tag,',') as tags, question_id from question_tag group by question_id) t on t.question_id=q.id " +
                    "left join (select count(*) as count, question_id from question_voting where action = 1 group by question_id) upvote on upvote.question_id = q.id " +
                    "left join (select count(*) as count, question_id from question_voting where action = 0 group by question_id) downvote on downvote.question_id = q.id " +
                    "where q.id=?");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new Exception("Question not found");
            }
            Feed feed = new Feed();
            feed.setId(resultSet.getInt("id"));
            feed.setUserId(resultSet.getInt("user_id"));
            feed.setTitle(resultSet.getString("title"));
            feed.setCreatedAt(resultSet.getString("created_at"));
            feed.setAnswer(resultSet.getString("answer"));
            feed.setAnswerUser(resultSet.getString("answerUser"));
            feed.setQuestionUser(resultSet.getString("questionUser"));
            feed.setTags(resultSet.getString("tags"));
            feed.setUpVotes(resultSet.getInt("upvotes"));
            feed.setDownVotes(resultSet.getInt("downvotes"));
            System.out.println(feed);

            //get user details
            User user = new User();
            statement = connection.prepareStatement("select * from user where id=?");
            statement.setString(1, feed.getUserId().toString());
            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new Exception("User not found.");
            }

            user.setId(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setEmail(resultSet.getString("email"));
            user.setUsername(resultSet.getString("username"));

            //get a list of all answers for this question
            //with user details (who posted this answer)
            //and count of upvotes for this answer
            statement = connection.prepareStatement("select a.*,CONCAT(u.first_name,' ',u.last_name) as answerUser, upvote.count as votes  from answer a left join (select count(*) as count, answer_id from answer_voting where action=1 group by answer_id) upvote on upvote.answer_id = a.id inner join user u on u.id=a.user_id where a.question_id=? order by upvote.count desc, a.id desc");
            statement.setString(1, feed.getId().toString());
            resultSet = statement.executeQuery();

            List<Answer> answers = new ArrayList<>();
            while (resultSet.next()) {
                Answer answer = new Answer();
                answer.setId(resultSet.getInt("id"));
                answer.setQuestionId(resultSet.getInt("question_id"));
                answer.setContent(resultSet.getString("content"));
                answer.setCreatedAt(resultSet.getString("created_at"));
                answer.setAnswerUser(resultSet.getString("answerUser"));
                answer.setUpVotes(resultSet.getInt("votes"));
                System.out.println(answer);
                answers.add(answer);
            }

            req.setAttribute("question", feed);
            req.setAttribute("user", user);
            req.setAttribute("answers", answers);
            System.out.println(answers.size());
            req.getRequestDispatcher("answer.jsp").forward(req, resp);




        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{
             String qid = req.getParameter("id");
             String answer = req.getParameter("answer");

            String userId = req.getSession().getAttribute("userId").toString();

            PreparedStatement statement = connection.prepareStatement("insert into answer (question_id,content,user_id) values(?,?,?)");
            statement.setString(1,qid);
            statement.setString(2,answer);
            statement.setString(3,userId);
            statement.executeUpdate();



            resp.sendRedirect(req.getContextPath() + "/answer?id=" + qid);


        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
