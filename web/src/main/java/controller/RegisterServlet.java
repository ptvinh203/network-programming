package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bo.UserBo;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserBo userBo = UserBo.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm-password");
        try {
            // Validate input
            if (email.isEmpty() || name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                throw new Exception("All fields are required!");
            }
            // Validate password
            if (!password.equals(confirmPassword)) {
                throw new Exception("Password and confirm password do not match!");
            }
            // Validate email
            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                throw new Exception("Invalid email!");
            }
            // Register
            userBo.register(email, confirmPassword, name);
            resp.sendRedirect("login");
        } catch (Exception e) {
            resp.sendRedirect(String.format("/web/register?error-message=%s", e.getMessage()));
            return;
        }
    }
}
