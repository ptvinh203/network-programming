package repository;

import model.bean.RequestBean;

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
}
