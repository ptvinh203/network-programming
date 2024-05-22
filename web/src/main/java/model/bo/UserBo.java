package model.bo;

import model.bean.UserBean;
import model.dto.UserDto;
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
    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;

    // Business Logic
    public UserDto login(String email, String password) throws Exception {
        try {
            UserBean user = userRepository.findByEmail(email);
            if (user != null && passwordUtil.hasMatches(password, user.getPassword())) {
                return new UserDto().fromBean(user);
            }
            throw new Exception("Email or password is incorrect!");
        } catch (Exception e) {
            throw new Exception("Account is not exist!");
        }
    }

    public void register(String email, String password, String name) throws Exception {
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
}
