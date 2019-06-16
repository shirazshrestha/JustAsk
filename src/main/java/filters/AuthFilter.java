package filters;

import controllers.DB;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebFilter("/*")
public class AuthFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = req.getServletPath();
        //allow user access login, register and home pages without
        //logging in
        if (path.equals("/login") || path.equals("/register") || path.equals("/")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;

        }

        HttpSession session = req.getSession();
        //If user session is empty
        //force user to login
        if (session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        //check if the user in session actually exists in our
        //database or not
        Integer id = (Integer) session.getAttribute("userId");


        Connection connection;
        connection = DB.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("select * from user where id=?");
            statement.setString(1, id.toString());
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                throw new Exception("User does not exist.");
            }
            filterChain.doFilter(req, resp);

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
    }

    @Override
    public void destroy() {


    }
}
