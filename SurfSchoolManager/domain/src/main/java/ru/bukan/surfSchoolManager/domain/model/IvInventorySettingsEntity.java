package ru.bukan.surfSchoolManager.domain.model;

import javax.persistence.*;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
@Entity
@Table(name = "iv_inventory_settings", schema = "public", catalog = "sfm_dev")
public class IvInventorySettingsEntity {
    private int id;
    private int inventoryType;
    private String propertyName;
    private String isRequired;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    @Column(name = "property_name", nullable = false, length = 200)
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Basic
    @Column(name = "is_required", nullable = false, length = -1)
    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IvInventorySettingsEntity that = (IvInventorySettingsEntity) o;

        if (id != that.id) return false;
        if (inventoryType != that.inventoryType) return false;
        if (propertyName != null ? !propertyName.equals(that.propertyName) : that.propertyName != null) return false;
        if (isRequired != null ? !isRequired.equals(that.isRequired) : that.isRequired != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + inventoryType;
        result = 31 * result + (propertyName != null ? propertyName.hashCode() : 0);
        result = 31 * result + (isRequired != null ? isRequired.hashCode() : 0);
        return result;
    }
}
