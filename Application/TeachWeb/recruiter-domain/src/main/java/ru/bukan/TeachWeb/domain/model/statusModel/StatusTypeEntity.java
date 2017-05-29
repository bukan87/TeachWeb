package ru.bukan.TeachWeb.domain.model.statusModel;

import javax.persistence.*;

/**
 * Created by Ilin on 29.05.2017.
 */
@Entity
@Table(name = "SM_STATUS_TYPE", schema = "REC")
public class StatusTypeEntity {
    private String code;
    private String statusTable;

    @Id
    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "STATUS_TABLE")
    public String getStatusTable() {
        return statusTable;
    }

    public void setStatusTable(String statusTable) {
        this.statusTable = statusTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusTypeEntity that = (StatusTypeEntity) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (statusTable != null ? !statusTable.equals(that.statusTable) : that.statusTable != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (statusTable != null ? statusTable.hashCode() : 0);
        return result;
    }
}
