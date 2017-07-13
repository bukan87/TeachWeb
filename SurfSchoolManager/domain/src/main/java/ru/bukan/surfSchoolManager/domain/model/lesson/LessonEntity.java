package ru.bukan.surfSchoolManager.domain.model.lesson;

import ru.bukan.surfSchoolManager.domain.model.customer.CustomerEntity;
import ru.bukan.surfSchoolManager.domain.model.instructor.InstructorEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
@Entity
@Table(name = "ls_lesson", schema = "public", catalog = "sfm_dev")
public class LessonEntity implements Serializable {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Integer lessonType;
    private Set<CustomerEntity> customers;
    private Set<InstructorEntity> instructors;

    @Id
    @Column(name = "id", nullable = false, insertable = false, updatable = false, unique = true)
    @SequenceGenerator(name = "lsLessonDdSeq", sequenceName = "ls_lesson_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsLessonDdSeq")
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

    public void setLessonType(Integer lessonType) {
        this.lessonType = lessonType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonEntity that = (LessonEntity) o;

        if (id != that.id) return false;
        if (lessonType != that.lessonType) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + lessonType;
        return result;
    }
}