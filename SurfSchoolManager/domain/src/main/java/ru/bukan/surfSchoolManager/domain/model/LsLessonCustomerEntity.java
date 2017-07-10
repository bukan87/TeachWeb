package ru.bukan.surfSchoolManager.domain.model;

import javax.persistence.*;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
@Entity
@Table(name = "ls_lesson_customer", schema = "public", catalog = "sfm_dev")
public class LsLessonCustomerEntity {
    private Long id;
    private Long lessonId;
    private Long customerId;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lesson_id", nullable = false)
    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    @Basic
    @Column(name = "customer_id", nullable = false)
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LsLessonCustomerEntity that = (LsLessonCustomerEntity) o;

        if (id != that.id) return false;
        if (lessonId != that.lessonId) return false;
        if (customerId != that.customerId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (lessonId ^ (lessonId >>> 32));
        result = 31 * result + (int) (customerId ^ (customerId >>> 32));
        return result;
    }
}
