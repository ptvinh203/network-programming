package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bo.RequestBo;
import model.dto.UserDto;
import utils.CommonConstant;
import utils.CookieUtil;
import utils.UserSessionUtil;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private final RequestBo requestBo = RequestBo.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Logout
            String isLogout = req.getParameter("logout");
            if (isLogout != null && isLogout.equals("true")) {
                HttpSession session = req.getSession();
                session.setAttribute(CommonConstant.CURRENT_USER, null);
                Cookie emailCookie = CookieUtil.getCookie(req, CommonConstant.EMAIL);
                Cookie passwordCookie = CookieUtil.getCookie(req, CommonConstant.PASSWORD);
                emailCookie.setMaxAge(0);
                passwordCookie.setMaxAge(0);
                resp.addCookie(emailCookie);
                resp.addCookie(passwordCookie);
                resp.sendRedirect(".");
                return;
            }

            // Login
            if (!UserSessionUtil.ensureUser(req, resp)) {
                resp.sendRedirect(".");
                return;
            }

            String userId = ((UserDto) req.getSession().getAttribute(CommonConstant.CURRENT_USER)).getId();
            req.getSession().setAttribute("requestDtoList", requestBo.getAllByUserId(userId));
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendRedirect(String.format("/web/home?error-message=%s", e.getMessage()));
            return;
        }
    }
}
