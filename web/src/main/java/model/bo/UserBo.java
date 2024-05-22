package model.bo;

import model.bean.UserBean;
import repository.UserRepository;
import utils.PasswordUtil;

public class UserBo {

    // Singleton
    private static UserBo instance;

    private UserBo() {
        userRepository = UserRepository.getInstance();
        passwordUtil = PasswordUtil.getInstance();
    }

    public static UserBo getInstance() {
        if (instance == null)
            instance = new UserBo();
        return instance;
    }

    // Dependency Injection
    private UserRepository userRepository;
    private PasswordUtil passwordUtil;

    // Business Logic
    public void register(String email, String password, String name) {
        try {
            userRepository.create(UserBean.builder()
                    .email(email)
                    .password(passwordUtil.encode(password))
                    .name(name)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserBean login(String email, String password) {
        try {
            UserBean user = userRepository.findByEmail(email);
            if (user != null && passwordUtil.hasMatches(password, user.getPassword())) {
                return user;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
