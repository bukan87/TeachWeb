package ru.bukan.TeachWeb.domain.model.statusModel;

import org.hibernate.Session;
import ru.bukan.TeachWeb.domain.util.HibernateSessionFactory;

/**
 * @author ilin_ai on 30.05.2017.
 */
public class StatusRepository {

    private Session session;

    public boolean isExistsTransition(String statusType, String fromStatus, String toStatus){
        session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        boolean result = session.get(StatusTransitionEntity.class, new StatusTransitionEntityPK(statusType, fromStatus, toStatus)) != null;
        session.close();
        return result;
    }
}
