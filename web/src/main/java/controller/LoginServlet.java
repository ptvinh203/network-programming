package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bo.UserBo;
import model.dto.UserDto;
import utils.CommonConstant;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserBo userBo = UserBo.getInstance();

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
            Cookie emailCookie = new Cookie(CommonConstant.EMAIL, email);
            Cookie passwordCookie = new Cookie(CommonConstant.PASSWORD, password);
            emailCookie.setMaxAge(60 * 60 * 24 * 30);
            passwordCookie.setMaxAge(60 * 60 * 24 * 30);
            resp.addCookie(emailCookie);
            resp.addCookie(passwordCookie);
            resp.sendRedirect("home");
        } catch (Exception e) {
            resp.sendRedirect(String.format("?error-message=%s", e.getMessage()));
            return;
        }
    }

}
