package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bo.UserBo;
import model.dto.UserDto;
import utils.UserSessionUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserBo userBo = UserBo.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (UserSessionUtil.ensureUser(req)) {
            resp.sendRedirect("home");
            return;
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            UserDto user = userBo.login(email, password);
            if (user == null) {
                resp.sendRedirect("?error-message=Login failed! Please check your email and password again.");
                return;
            }
            HttpSession session = req.getSession();
            session.setAttribute("email", email);
            session.setAttribute("password", password);
            session.setAttribute("current_user", user);
            resp.sendRedirect("home");
        } catch (Exception e) {
            resp.sendRedirect(String.format("?error-message=%s", e.getMessage()));
            return;
        }
    }

}
