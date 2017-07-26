package ru.bukan.ssm.domain.model.lesson;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
@Entity
@Table(name = "ls_lesson_customer", schema = "public", catalog = "sfm_dev")
public class LessonCustomerEntity implements Serializable {
    private Long id;
    private Long lessonId;
    private Long customerId;
    private LessonEntity lesson;

    @Id
    @Column(name = "id", nullable = false, updatable = false, insertable = false)
    @SequenceGenerator(name = "lessonCustomerIdSeq", sequenceName = "ls_lesson_customer_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessonCustomerIdSeq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lesson_id", nullable = false, insertable = false, updatable = false)
    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    @Basic
    @Column(name = "customer_id", nullable = false, insertable = false, updatable = false)
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public LessonEntity getLesson() {
        return lesson;
    }

    public void setLesson(LessonEntity lesson) {
        this.lesson = lesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonCustomerEntity that = (LessonCustomerEntity) o;

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
