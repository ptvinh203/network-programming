package repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fasterxml.uuid.Generators;

import model.bean.UserBean;
import utils.HibernateUtil;

public class UserRepository {

    private static UserRepository instance;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null)
            instance = new UserRepository();
        return instance;
    }

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public UserBean findById(int userId) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            return session.find(UserBean.class, userId);
        }
    }

    public UserBean findByEmail(String email) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM UserBean WHERE email = :email", UserBean.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }

    public UserBean create(UserBean user) throws Exception {
        user.setId(Generators.randomBasedGenerator().generate().toString());
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return user;
    }

}
