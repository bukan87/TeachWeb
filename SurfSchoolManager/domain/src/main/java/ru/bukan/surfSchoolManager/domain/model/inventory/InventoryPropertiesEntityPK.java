package ru.bukan.surfSchoolManager.domain.model.inventory;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
public class InventoryPropertiesEntityPK implements Serializable {
    private Long inventoryId;
    private int settingId;

    @Column(name = "inventory_id", nullable = false)
    @Id
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Column(name = "setting_id", nullable = false)
    @Id
    public int getSettingId() {
        return settingId;
    }

    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InventoryPropertiesEntityPK that = (InventoryPropertiesEntityPK) o;

        if (inventoryId != that.inventoryId) return false;
        if (settingId != that.settingId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (inventoryId ^ (inventoryId >>> 32));
        result = 31 * result + settingId;
        return result;
    }
}
