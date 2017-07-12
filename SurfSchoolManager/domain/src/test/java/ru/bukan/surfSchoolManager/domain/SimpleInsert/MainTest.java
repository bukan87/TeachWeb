package ru.bukan.surfSchoolManager.domain.SimpleInsert;

import org.junit.Test;
import ru.bukan.surfSchoolManager.domain.BasicTest;
import ru.bukan.surfSchoolManager.domain.model.lesson.LessonEntity;

/**
 * @author by Ilin_ai on 11.07.2017.
 */
public class MainTest extends BasicTest {

    @Test
    public void createLesson(){
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonType(1);
        java.util.Date d = new java.util.Date();
        lessonEntity.setStartDate(d);
        getDao().save(d);
        /*LessonEntity lesson = new LessonEntity();
        lesson.setLessonType(1);
        java.util.Date d = new java.util.Date();
        Timestamp t = new Timestamp(d.getTime());
        lesson.setStartDate(t);
        Assert.assertNotNull(getDao());
        getDao().save(lesson);*/
    }
}