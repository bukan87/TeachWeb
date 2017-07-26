package ru.bukan.ssm.domain.model.lesson;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
@Entity
@Table(name = "ls_lesson_instructor", schema = "public", catalog = "sfm_dev")
public class LessonInstructorEntity implements Serializable {
    private Long id;
    private Long lessonId;
    private Long instructorId;
    private LessonEntity lesson;

    @Id
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
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
    @Column(name = "instructor_id", nullable = false, insertable = false, updatable = false)
    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
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

        LessonInstructorEntity that = (LessonInstructorEntity) o;

        if (id != that.id) return false;
        if (lessonId != that.lessonId) return false;
        if (instructorId != that.instructorId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (lessonId ^ (lessonId >>> 32));
        result = 31 * result + (int) (instructorId ^ (instructorId >>> 32));
        return result;
    }
}
