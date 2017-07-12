package ru.bukan.TeachWeb.domain.model.statusModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author by Ilin on 29.05.2017.
 */
@Entity
@Table(name = "SM_STATUS_HISTORY")
@XmlRootElement(name = "statsHistory")
public class StatusHistoryEntity {
    private long id;
    private long tableId;
    private String statusType;
    private String fromStatus;
    private String toStatus;
    private Date changeStatusDate;

    @Id
    @Column(name = "ID", updatable = false)
    @SequenceGenerator(name = "sm_hst_id_seq", sequenceName = "sm_hst_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "sm_hst_id_seq", strategy = GenerationType.SEQUENCE)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TABLE_ID")
    @XmlElement
    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    @Basic
    @Column(name = "STATUS_TYPE")
    @XmlElement
    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    @Basic
    @Column(name = "FROM_STATUS")
    @XmlElement
    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }

    @Basic
    @Column(name = "TO_STATUS")
    @XmlElement
    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }

    @Basic
    @Column(name = "CHANGE_STATUS_DATE", insertable = false)
    @XmlElement
    public Date getChangeStatusDate() {
        return changeStatusDate;
    }

    public void setChangeStatusDate(Date changeStatusDate) {
        this.changeStatusDate = changeStatusDate;
    }

    public StatusHistoryEntity(long tableId, String statusType, String fromStatus, String toStatus) {
        this.tableId = tableId;
        this.statusType = statusType;
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
    }

    public StatusHistoryEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusHistoryEntity that = (StatusHistoryEntity) o;

        if (id != that.id) return false;
        if (tableId != that.tableId) return false;
        if (statusType != null ? !statusType.equals(that.statusType) : that.statusType != null) return false;
        if (fromStatus != null ? !fromStatus.equals(that.fromStatus) : that.fromStatus != null) return false;
        if (toStatus != null ? !toStatus.equals(that.toStatus) : that.toStatus != null) return false;
        if (changeStatusDate != null ? !changeStatusDate.equals(that.changeStatusDate) : that.changeStatusDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (tableId ^ (tableId >>> 32));
        result = 31 * result + (statusType != null ? statusType.hashCode() : 0);
        result = 31 * result + (fromStatus != null ? fromStatus.hashCode() : 0);
        result = 31 * result + (toStatus != null ? toStatus.hashCode() : 0);
        result = 31 * result + (changeStatusDate != null ? changeStatusDate.hashCode() : 0);
        return result;
    }
}
