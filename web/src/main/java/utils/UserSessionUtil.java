package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.bo.UserBo;
import model.dto.UserDto;

public class UserSessionUtil {

    static private UserBo userBo = UserBo.getInstance();

    static public boolean ensureUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Cookie emailCookie = CookieUtil.getCookie(req, "email");
        Cookie passwordCookie = CookieUtil.getCookie(req, "password");

        if (emailCookie == null) {
            session.setAttribute("email", null);
            return false;
        }

        if (passwordCookie == null) {
            session.setAttribute("password", null);
            return false;
        }

        try {
            UserDto user = userBo.login(emailCookie.getValue(), passwordCookie.getValue());
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
