package ru.bukan.surfSchoolManager.domain.model.instructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author by Ilin_ai on 03.07.2017.
 */
public class AvailableLessonTypeEntityPK implements Serializable {
    private Long instructorId;
    private Integer lessonType;

    @Column(name = "instructor_id", nullable = false)
    @Id
    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    @Column(name = "lesson_type", nullable = false)
    @Id
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

        AvailableLessonTypeEntityPK that = (AvailableLessonTypeEntityPK) o;

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
