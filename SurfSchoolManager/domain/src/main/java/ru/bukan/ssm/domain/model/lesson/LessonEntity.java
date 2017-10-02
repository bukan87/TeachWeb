package ru.bukan.ssm.domain.model.lesson;

import ru.bukan.ssm.domain.model.customer.CustomerEntity;
import ru.bukan.ssm.domain.model.instructor.InstructorEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
@Entity
@Table(name = "ls_lesson", schema = "public", catalog = "sfm_dev")
@SqlResultSetMapping(name = "UpdateStatistics"
        , classes = {
        @ConstructorResult(targetClass = ru.bukan.ssm.domain.model.Stats.class,
                columns = {
                        @ColumnResult(name = "d", type = Date.class),
                        @ColumnResult(name = "cnt", type = Integer.class)
                })
})
public class LessonEntity implements Serializable {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Integer lessonType;
    private Set<CustomerEntity> customers;
    private Set<InstructorEntity> instructors;
    /*private Set<LessonCustomerEntity> lessonCustomers;
    private Set<LessonInstructorEntity> lessonInstructors;*/

    @Id
    @Column(name = "id", nullable = false, insertable = false, updatable = false, unique = true)
    @SequenceGenerator(name = "lsLessonIdSeq", sequenceName = "ls_lesson_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsLessonIdSeq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "lesson_type", nullable = false)
    public Integer getLessonType() {
        return lessonType;
    }

    public void setLessonType(Integer lessonType) {
        this.lessonType = lessonType;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ls_lesson_customer", joinColumns = {@JoinColumn(name = "lesson_id")}, inverseJoinColumns = {@JoinColumn(name = "customer_id")})
    public Set<CustomerEntity> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<CustomerEntity> customers) {
        this.customers = customers;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ls_lesson_instructor", joinColumns = {@JoinColumn(name = "lesson_id")}, inverseJoinColumns = {@JoinColumn(name = "instructor_id")})
    public Set<InstructorEntity> getInstructors() {
        return instructors;
    }

    public void setInstructors(Set<InstructorEntity> instructors) {
        this.instructors = instructors;
    }
    /*
    @OneToMany(fetch = FetchType.LAZY)
    public Set<LessonCustomerEntity> getLessonCustomers() {
        return lessonCustomers;
    }

    public void setLessonCustomers(Set<LessonCustomerEntity> lessonCustomers) {
        this.lessonCustomers = lessonCustomers;
    }

    @OneToMany(fetch = FetchType.LAZY)
    public Set<LessonInstructorEntity> getLessonInstructors() {
        return lessonInstructors;
    }

    public void setLessonInstructors(Set<LessonInstructorEntity> lessonInstructors) {
        this.lessonInstructors = lessonInstructors;
    }
    */
    public LessonEntity(){}

    public LessonEntity(Long id, Date startDate, Date endDate, Integer lessonType, Set<CustomerEntity> customers, Set<InstructorEntity> instructors) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lessonType = lessonType;
        this.customers = customers;
        this.instructors = instructors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonEntity)) return false;

        LessonEntity that = (LessonEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (lessonType != null ? !lessonType.equals(that.lessonType) : that.lessonType != null) return false;
        if (customers != null ? !customers.equals(that.customers) : that.customers != null) return false;
        return instructors != null ? instructors.equals(that.instructors) : that.instructors == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (lessonType != null ? lessonType.hashCode() : 0);
        result = 31 * result + (customers != null ? customers.hashCode() : 0);
        result = 31 * result + (instructors != null ? instructors.hashCode() : 0);
        return result;
    }
}