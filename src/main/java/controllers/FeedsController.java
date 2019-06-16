package controllers;

import services.FeedService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

@WebServlet("/feed")
public class FeedsController extends HttpServlet {
    private FeedService service;

    @Override
    public void init() throws ServletException {
        service = new FeedService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection connection = DB.getConnection();
        try {
            req.setAttribute("feeds", service.getAllFeeds());
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("feeds.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
