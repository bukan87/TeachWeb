package ru.bukan.ssm.domain.model;

import ru.bukan.ssm.domain.model.lesson.LessonEntity;

import java.util.Set;

/**
 * Интерфейс по работе с клиентами и инструкторами
 * @author by Ilin_ai on 27.07.2017.
 */
public interface Person {

    Long getId();

    void setId(Long id);

    String getLastName();

    void setLastName(String lastName);

    String getFirstName();

    void setFirstName(String firstName);

    String getMiddleName();

    void setMiddleName(String middleName);

    Set<LessonEntity> getLessons();

    void setLessons(Set<LessonEntity> lessons);
}
