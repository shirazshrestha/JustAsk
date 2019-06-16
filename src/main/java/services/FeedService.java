package services;

import controllers.DB;
import entity.Feed;

import java.sql.Connection;
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
            ResultSet result = connection.prepareStatement("select * from question").executeQuery();
            List<Feed> feeds = new ArrayList<>();
            while (result.next()) {
                Feed feed = new Feed();
                feed.setId(result.getInt("id"));
                feed.setTitle(result.getString("title"));
                feeds.add(feed);
            }
            return feeds;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
