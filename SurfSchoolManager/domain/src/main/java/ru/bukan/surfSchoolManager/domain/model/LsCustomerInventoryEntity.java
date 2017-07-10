package ru.bukan.surfSchoolManager.domain.model;

import javax.persistence.*;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
@Entity
@Table(name = "ls_customer_inventory", schema = "public", catalog = "sfm_dev")
@IdClass(LsCustomerInventoryEntityPK.class)
public class LsCustomerInventoryEntity {
    private Long id;
    private Long lesCustomerId;
    private Long inventoryId;
    private Long inventoryType;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "les_customer_id", nullable = false)
    public Long getLesCustomerId() {
        return lesCustomerId;
    }

    public void setLesCustomerId(Long lesCustomerId) {
        this.lesCustomerId = lesCustomerId;
    }

    @Basic
    @Column(name = "inventory_id", nullable = false)
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Id
    @Column(name = "inventory_type", nullable = false)
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

        LsCustomerInventoryEntity that = (LsCustomerInventoryEntity) o;

        if (id != that.id) return false;
        if (lesCustomerId != that.lesCustomerId) return false;
        if (inventoryId != that.inventoryId) return false;
        if (inventoryType != that.inventoryType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (lesCustomerId ^ (lesCustomerId >>> 32));
        result = 31 * result + (int) (inventoryId ^ (inventoryId >>> 32));
        result = 31 * result + (int) (inventoryType ^ (inventoryType >>> 32));
        return result;
    }
}
