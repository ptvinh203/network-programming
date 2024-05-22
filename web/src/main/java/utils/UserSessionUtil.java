package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bo.UserBo;
import model.dto.UserDto;

public class UserSessionUtil {

    static private UserBo userBo = UserBo.getInstance();

    static public boolean ensureUser(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Cookie emailCookie = CookieUtil.getCookie(req, CommonConstant.EMAIL);
        Cookie passwordCookie = CookieUtil.getCookie(req, CommonConstant.PASSWORD);

        if (emailCookie == null || passwordCookie == null) {
            session.setAttribute(CommonConstant.CURRENT_USER, null);
            return false;
        }

        try {
            UserDto user = userBo.login(emailCookie.getValue(), passwordCookie.getValue());
            if (user == null) {
                session.setAttribute(CommonConstant.CURRENT_USER, null);
                emailCookie.setMaxAge(0);
                passwordCookie.setMaxAge(0);
                resp.addCookie(emailCookie);
                resp.addCookie(passwordCookie);
                return false;
            }
            session.setAttribute(CommonConstant.CURRENT_USER, user);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
