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
        this.currentStatus = currentStatus;
    }

    public StatusTable(String statusType, String currentStatus) {
        this.statusType = statusType;
        this.currentStatus = currentStatus;
    }

    protected void changeStatus(Long id, String status){
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

    /**
    * Обязательный метод по смене статуса. Должен дёргать метод changeStatus(Long, String)
    */
    abstract public void changeStatus(String status);
}