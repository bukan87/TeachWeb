package ru.bukan.surfSchoolManager.domain.model.lesson;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
public class CustomerInventoryEntityPK implements Serializable {
    private Long id;
    private Long inventoryType;

    @Column(name = "id", nullable = false)
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "inventory_type", nullable = false)
    @Id
    public Long getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(Long inventoryType) {
        this.inventoryType = inventoryType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerInventoryEntityPK that = (CustomerInventoryEntityPK) o;

        if (id != that.id) return false;
        if (inventoryType != that.inventoryType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (inventoryType ^ (inventoryType >>> 32));
        return result;
    }
}
