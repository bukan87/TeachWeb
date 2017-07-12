package ru.bukan.surfSchoolManager.domain.model.lesson;

import javax.persistence.*;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
@Entity
@Table(name = "ls_lesson_instructor", schema = "public", catalog = "sfm_dev")
public class LessonInstructorEntity {
    private Long id;
    private Long lessonId;
    private Long instructorId;

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
    @Column(name = "instructor_id", nullable = false)
    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
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
