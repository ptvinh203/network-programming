package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.bo.UserBo;
import model.dto.UserDto;

public class UserSessionUtil {

    static private UserBo userBo = UserBo.getInstance();

    static public boolean ensureUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("email");
        String password = (String) session.getAttribute("password");

        if (email == null) {
            session.setAttribute("email", null);
            return false;
        }

        if (password == null) {
            session.setAttribute("password", null);
            return false;
        }

        try {
            UserDto user = userBo.login(email, password);
            if (user == null) {
                session.setAttribute("email", null);
                session.setAttribute("password", null);
                return false;
            }

            session.setAttribute("current_user", user);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
