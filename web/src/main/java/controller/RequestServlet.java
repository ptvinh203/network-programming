package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.bo.RequestBo;
import model.dto.UserDto;

@WebServlet("/request")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class RequestServlet extends HttpServlet {

    private final RequestBo requestBo = RequestBo.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part baseImg = req.getPart("base_img");
        Part compareImg = req.getPart("compare_img");
        String userId = ((UserDto) req.getSession().getAttribute("current_user")).getId();
        try {
            requestBo.create(userId, baseImg, compareImg);
            resp.sendRedirect("home.jsp");
        } catch (Exception e) {
            resp.sendRedirect(String.format("/web/home?error-message=%s", e.getMessage()));
            return;
        }
    }
}
