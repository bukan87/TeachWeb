package ru.bukan.TeachWeb.domain.model.statusModel;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * Абстрактный класс по работе со статусной моделью
 *
 * @author by Ilin_ai on 30.05.2017.
 */
@MappedSuperclass
public abstract class StatusTable {

    private String statusType;
    private String currentStatus;

    @Column(name = "status_type", nullable = false)
    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    @Column(name = "status", nullable = false)
    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        if (new StatusRepository().isExistsTransition(statusType, this.currentStatus, currentStatus)) {
            this.currentStatus = currentStatus;
            /*Session session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();

            StatusHistoryEntity history = new StatusHistoryEntity(getId(), statusType, currentStatus, currentStatus);
            session.save(history);

            session.getTransaction().commit();
            session.close();*/
        }
    }


    public StatusTable(String statusType, String currentStatus) {
        this.statusType = statusType;
        this.currentStatus = currentStatus;
    }

}