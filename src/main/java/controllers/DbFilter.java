package controllers;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter("/")
public class DbFilter implements Filter {


    public void init(FilterConfig config) {

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            request.setAttribute("db", DB.getConnection());
            System.out.println("db connected");
        } catch (Exception e) {
            System.err.println("Error during connection with db : " + e);
        }
        chain.doFilter(req, res);
    }

    public void destroy() {

    }

}
