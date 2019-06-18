package controllers;

import entity.Answer;
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
            String tag = req.getParameter("tag");
            if(tag != null){
                req.setAttribute("feeds", service.getFeedsByTag(tag));
            }else{
                req.setAttribute("feeds", service.getAllFeeds());
            }


            //get the question_id from url
            String id = req.getParameter("id");
            System.out.println(id);

            //get question from database
            PreparedStatement statement = connection.prepareStatement("select * from question where id=?");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new Exception("Question not found");
            }
            Question question = new Question();
            question.setId(resultSet.getInt("id"));
            question.setTitle(resultSet.getString("title"));
            question.setUserId(resultSet.getInt("user_id"));

            //get user details
            User user = new User();
            statement = connection.prepareStatement("select * from user where id=?");
            statement.setString(1, question.getUserId().toString());
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
            statement = connection.prepareStatement("select * from answer where question_id=?");
            statement.setString(1, question.getId().toString());
            resultSet = statement.executeQuery();

            List<Answer> answers = new ArrayList<>();
            while (resultSet.next()) {
                Answer answer = new Answer();
                answer.setId(resultSet.getInt("id"));
                answer.setQuestionId(resultSet.getInt("question_id"));
                answer.setContent(resultSet.getString("content"));
                answer.setCreatedAt(resultSet.getString("created_at"));
                answers.add(answer);
            }

            req.setAttribute("question", question);
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
