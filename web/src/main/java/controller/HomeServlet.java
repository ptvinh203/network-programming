package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.UserSessionUtil;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Logout
        String isLogout = req.getParameter("logout");
        if (isLogout != null && isLogout.equals("true")) {
            HttpSession session = req.getSession();
            session.setAttribute("email", null);
            session.setAttribute("password", null);
            session.setAttribute("current_user", null);
            resp.sendRedirect(".");
            return;
        }

        // Login
        if (!UserSessionUtil.ensureUser(req)) {
            resp.sendRedirect(".");
            return;
        }
        req.getRequestDispatcher("home.jsp").forward(req, resp);
    }
}
