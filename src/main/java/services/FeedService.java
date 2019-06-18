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

public class FeedService {
    Connection connection;

    public FeedService() {

        connection = DB.getConnection();
    }

    public List<Feed> getAllFeeds() {
        try {
            ResultSet result = connection.prepareStatement("select q.*, a.content as answer, a.user as answerUser, concat(u.first_name,' ',u.last_name) as questionUser, t.tags, upvote.count as upvotes, downvote.count as downvotes from question q left join ( SELECT a.*,concat(u.first_name,' ',u.last_name) as user FROM answer a inner join user u on a.user_id = u.id WHERE a.id = ( SELECT max(id) FROM answer WHERE a.question_id = question_id ) ) a on a.question_id = q.id inner join user u on u.id = q.user_id left join (select group_concat(tag,',') as tags, question_id from question_tag group by question_id) t on t.question_id=q.id left join (select count(*) as count, question_id from question_voting where action = 1 group by question_id) upvote on upvote.question_id = q.id left join (select count(*) as count, question_id from question_voting where action = 0 group by question_id) downvote on downvote.question_id = q.id ORDER by created_at desc LIMIT 0,10").executeQuery();
            List<Feed> feeds = new ArrayList<>();
            while (result.next()) {
                Feed feed = new Feed();
                feed.setId(result.getInt("id"));
                feed.setTitle(result.getString("title"));
                feed.setCreatedAt(result.getString("created_at"));
                feed.setAnswer(result.getString("answer"));
                feed.setAnswerUser(result.getString("answerUser"));
                feed.setQuestionUser(result.getString("questionUser"));
                feed.setTags(result.getString("tags"));
                feed.setUpVotes(result.getInt("upvotes"));
                feed.setDownVotes(result.getInt("downvotes"));
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

    public List<Feed> getFeedsByTag(String tag) {

        List<Feed> feeds = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("select q.*, a.content as answer, a.user as answerUser, concat(u.first_name,' ',u.last_name) as questionUser, t.tags, upvote.count as upvotes, downvote.count as downvotes from question q left join ( SELECT a.*,concat(u.first_name,' ',u.last_name) as user FROM answer a inner join user u on a.user_id = u.id WHERE a.id = ( SELECT max(id) FROM answer WHERE a.question_id = question_id ) ) a on a.question_id = q.id inner join user u on u.id = q.user_id left join (select group_concat(tag,',') as tags, question_id from question_tag group by question_id) t on t.question_id=q.id left join (select count(*) as count, question_id from question_voting where action = 1 group by question_id) upvote on upvote.question_id = q.id left join (select count(*) as count, question_id from question_voting where action = 0 group by question_id) downvote on downvote.question_id = q.id  WHERE q.id IN (SELECT question_id FROM question_tag WHERE tag = ?) ORDER by created_at desc LIMIT 0,10");
            statement.setString(1, tag);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Feed feed = new Feed();
                feed.setId(result.getInt("id"));
                feed.setTitle(result.getString("title"));
                feed.setCreatedAt(result.getString("created_at"));
                feed.setAnswer(result.getString("answer"));
                feed.setAnswerUser(result.getString("answerUser"));
                feed.setQuestionUser(result.getString("questionUser"));
                feed.setTags(result.getString("tags"));
                feed.setUpVotes(result.getInt("upvotes"));
                feed.setDownVotes(result.getInt("downvotes"));
                feeds.add(feed);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return feeds;
    }
}
