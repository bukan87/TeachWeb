package ru.bukan.ssm.domain.model.instructor;

import javax.persistence.*;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
@Entity
@Table(name = "it_available_lesson_type", schema = "public", catalog = "sfm_dev")
@IdClass(AvailableLessonTypeEntityPK.class)
public class AvailableLessonTypeEntity {
    private Long instructorId;
    private Integer lessonType;

    @Id
    @Column(name = "instructor_id", nullable = false)
    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    @Id
    @Column(name = "lesson_type", nullable = false)
    public Integer getLessonType() {
        return lessonType;
    }

    public void setLessonType(Integer lessonType) {
        this.lessonType = lessonType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AvailableLessonTypeEntity that = (AvailableLessonTypeEntity) o;

        if (instructorId != that.instructorId) return false;
        if (lessonType != that.lessonType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (instructorId ^ (instructorId >>> 32));
        result = 31 * result + lessonType;
        return result;
    }
}
