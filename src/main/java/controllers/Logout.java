package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/logout")
public class Logout extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        HttpSession session= req.getSession();
        if(session.getAttribute("userId") == null){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        session.removeAttribute("userId");
        resp.sendRedirect(req.getContextPath() );

    }
}
