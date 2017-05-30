package ru.bukan.TeachWeb.domain.model.statusModel;

import org.hibernate.Session;
import ru.bukan.TeachWeb.domain.util.HibernateSessionFactory;

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

    @Column(name = "status_type", nullable = false)
    private String statusType;
    @Column(name = "status", nullable = false)
    private String currentStatus;

    public String getStatusType() {
        return statusType;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public StatusTable(String statusType, String currentStatus) {
        this.statusType = statusType;
        this.currentStatus = currentStatus;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void changeStatus(Long id, String status){
        if (new StatusRepository().isExistsTransition(statusType, currentStatus, status)){
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            currentStatus = status;
            session.saveOrUpdate(this);

            // Запись в историю изменения статуса
            StatusHistoryEntity history = new StatusHistoryEntity(id, statusType, currentStatus, status);
            session.save(history);

            session.getTransaction().commit();
            session.close();
        }
    }
}