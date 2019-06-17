package controllers;

import entity.Tag;
import services.FeedService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/feed")
public class FeedsController extends HttpServlet {
    private FeedService service;

    @Override
    public void init() throws ServletException {
        service = new FeedService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("tags", service.getAllTags());
            req.setAttribute("feeds", service.getAllFeeds());
        } catch (Exception e) {
            e.getMessage();
            req.setAttribute("tags", new ArrayList<Tag>());
        }
        req.getRequestDispatcher("feeds.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
