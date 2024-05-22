package repository;

import org.hibernate.Session;

import model.bean.UserBean;

public class UserRepository extends BaseRepository<UserBean, String> {

    // Singleton
    private static UserRepository instance;

    private UserRepository() {
        super(UserBean.class);
    }

    public static UserRepository getInstance() {
        if (instance == null)
            instance = new UserRepository();
        return instance;
    }

    // Methods
    public UserBean findByEmail(String email) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM UserBean WHERE email = :email", UserBean.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }
}
