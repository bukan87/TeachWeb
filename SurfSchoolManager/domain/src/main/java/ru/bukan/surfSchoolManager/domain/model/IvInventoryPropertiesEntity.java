package ru.bukan.surfSchoolManager.domain.model;

import javax.persistence.*;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
@Entity
@Table(name = "iv_inventory_properties", schema = "public", catalog = "sfm_dev")
@IdClass(IvInventoryPropertiesEntityPK.class)
public class IvInventoryPropertiesEntity {
    private Long inventoryId;
    private int settingId;
    private String propertyVal;

    @Id
    @Column(name = "inventory_id", nullable = false)
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Id
    @Column(name = "setting_id", nullable = false)
    public int getSettingId() {
        return settingId;
    }

    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    @Basic
    @Column(name = "property_val", nullable = false, length = 200)
    public String getPropertyVal() {
        return propertyVal;
    }

    public void setPropertyVal(String propertyVal) {
        this.propertyVal = propertyVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IvInventoryPropertiesEntity that = (IvInventoryPropertiesEntity) o;

        if (inventoryId != that.inventoryId) return false;
        if (settingId != that.settingId) return false;
        if (propertyVal != null ? !propertyVal.equals(that.propertyVal) : that.propertyVal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (inventoryId ^ (inventoryId >>> 32));
        result = 31 * result + settingId;
        result = 31 * result + (propertyVal != null ? propertyVal.hashCode() : 0);
        return result;
    }
}
