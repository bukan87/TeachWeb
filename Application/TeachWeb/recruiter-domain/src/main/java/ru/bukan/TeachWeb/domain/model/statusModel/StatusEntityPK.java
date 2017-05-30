package ru.bukan.TeachWeb.domain.model.statusModel;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Ilin on 29.05.2017.
 */
public class StatusEntityPK implements Serializable {
    private String statusType;
    private String code;

    @Column(name = "STATUS_TYPE")
    @Id
    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    @Column(name = "CODE")
    @Id
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusEntityPK that = (StatusEntityPK) o;

        if (statusType != null ? !statusType.equals(that.statusType) : that.statusType != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = statusType != null ? statusType.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    public StatusEntityPK(String statusType, String code) {
        this.statusType = statusType;
        this.code = code;
    }

    public StatusEntityPK() {
    }
}
