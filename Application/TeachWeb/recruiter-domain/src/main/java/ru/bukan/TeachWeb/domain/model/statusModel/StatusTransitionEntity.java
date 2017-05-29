package ru.bukan.TeachWeb.domain.model.statusModel;

import javax.persistence.*;

/**
 * Created by Ilin on 29.05.2017.
 */
@Entity
@Table(name = "SM_STATUS_TRANSITION", schema = "REC")
@IdClass(StatusTransitionEntityPK.class)
public class StatusTransitionEntity {
    private String statusType;
    private String fromStatus;
    private String toStatus;

    @Id
    @Column(name = "STATUS_TYPE")
    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    @Id
    @Column(name = "FROM_STATUS")
    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }

    @Id
    @Column(name = "TO_STATUS")
    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusTransitionEntity that = (StatusTransitionEntity) o;

        if (statusType != null ? !statusType.equals(that.statusType) : that.statusType != null) return false;
        if (fromStatus != null ? !fromStatus.equals(that.fromStatus) : that.fromStatus != null) return false;
        if (toStatus != null ? !toStatus.equals(that.toStatus) : that.toStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = statusType != null ? statusType.hashCode() : 0;
        result = 31 * result + (fromStatus != null ? fromStatus.hashCode() : 0);
        result = 31 * result + (toStatus != null ? toStatus.hashCode() : 0);
        return result;
    }

}
