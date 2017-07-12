package ru.bukan.surfSchoolManager.domain.model.inventory;

import javax.persistence.*;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
@Entity
@Table(name = "iv_inventory", schema = "public", catalog = "sfm_dev")
public class InventoryEntity {
    private Long id;
    private int inventoryType;
    private String name;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "inventory_type", nullable = false)
    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InventoryEntity that = (InventoryEntity) o;

        if (id != that.id) return false;
        if (inventoryType != that.inventoryType) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + inventoryType;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
