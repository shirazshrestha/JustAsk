package services;

import controllers.DB;
import entity.Answer;
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
}
