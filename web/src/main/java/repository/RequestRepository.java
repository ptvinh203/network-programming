package repository;

import model.bean.RequestBean;

import java.util.List;

import org.hibernate.Session;

public class RequestRepository extends BaseRepository<RequestBean, String> {

    // Singleton
    private static RequestRepository instance;

    private RequestRepository() {
        super(RequestBean.class);
    }

    public static RequestRepository getInstance() {
        if (instance == null)
            instance = new RequestRepository();
        return instance;
    }

    // Methods
    public List<RequestBean> getAllByUserId(String userId) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM RequestBean r WHERE r.user.id = :userId", RequestBean.class)
                    .setParameter("userId", userId).getResultList();
        }
    }
}
