package services;

import controllers.DB;
import entity.Feed;
import entity.Question;
import entity.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AnswerService {
    Connection connection;

    public AnswerService() {
        this.connection = DB.getConnection();
    }

    public List<Feed> getAllFeeds() {
        try {
            ResultSet result = connection.prepareStatement("select * from question LIMIT 5").executeQuery();
            List<Feed> feeds = new ArrayList<>();
            while (result.next()) {
                Feed feed = new Feed();
                feed.setId(result.getInt("id"));
                feed.setTitle(result.getString("title"));
                feed.setCreatedAt(result.getString("created_at"));
                feeds.add(feed);
            }
            return feeds;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        try {

            PreparedStatement statement = connection.prepareStatement("select count(*) as count,id,question_id, tag from question_tag group by tag");
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                Tag tag = new Tag();
                tag.setId(results.getInt("id"));
                tag.setQuestionId(results.getInt("question_id"));
                tag.setTag(results.getString("tag"));
                tags.add(tag);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tags;
    }

    public List<Question> getFeedsByTag(String tag) {

        List<Question> questions = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("select * from question WHERE id IN (SELECT question_id FROM question_tag WHERE tag = ?)");
            statement.setString(1, tag);

            ResultSet results = statement.executeQuery();
            while (results.next()) {
                Question question = new Question();
                question.setId(results.getInt("id"));
                question.setCreatedAt(results.getString("created_at"));
                question.setTitle(results.getString("title"));
                questions.add(question);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return questions;
    }
}
