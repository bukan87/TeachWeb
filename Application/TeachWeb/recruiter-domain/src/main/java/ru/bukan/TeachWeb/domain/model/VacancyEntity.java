package ru.bukan.TeachWeb.domain.model;

import org.hibernate.Session;
import ru.bukan.TeachWeb.domain.model.statusModel.StatusTable;
import ru.bukan.TeachWeb.domain.util.HibernateSessionFactory;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author by Ilin_ai on 30.05.2017.
 */
@Entity
@Table(name = "VN_VACANCY")
@AttributeOverrides({
        @AttributeOverride(name = "statusType", column = @Column(name = "status_type")),
        @AttributeOverride(name = "currentStatus", column = @Column(name = "status"))
})
@XmlRootElement(name = "vacancy")
public class VacancyEntity extends StatusTable{
    private long id;
    private String name;

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(name = "vn_vcn_id_seq", sequenceName = "vn_vcn_id_seq")
    @GeneratedValue(generator = "vn_vcn_id_seq", strategy = GenerationType.SEQUENCE)
    @XmlTransient
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 250)
    @XmlElement
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

        VacancyEntity that = (VacancyEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public VacancyEntity() {
        super("vacancy", "draft");
    }

    public VacancyEntity(Long id){
        this();
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        VacancyEntity vacancyEntity = session.get(this.getClass(), id);
        this.id = vacancyEntity.getId();
        this.name = vacancyEntity.getName();
        session.close();
    }

    @Override
    public void changeStatus(String status){
        super.changeStatus(id, status);
    }
}
