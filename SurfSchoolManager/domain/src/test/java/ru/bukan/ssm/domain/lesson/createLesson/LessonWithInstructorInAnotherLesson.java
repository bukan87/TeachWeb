package ru.bukan.ssm.domain.lesson.createLesson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.bukan.ssm.domain.BasicTest;
import ru.bukan.ssm.domain.model.Person;
import ru.bukan.ssm.domain.model.instructor.InstructorEntity;
import ru.bukan.ssm.domain.model.lesson.InAnotherLessonException;
import ru.bukan.ssm.domain.model.lesson.LessonEntity;
import ru.bukan.ssm.domain.testUtil.Generate;
import ru.bukan.ssm.domain.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Создание урока с клиентом, находящемся на другом уроке в это же время
 * |-------| - продолжительность урока
 * верхний урок это первый(уже созданный урок)
 * нижний второй
 *
 * @author by Ilin_ai on 31.08.2017.
 */
public class LessonWithInstructorInAnotherLesson extends BasicTest {

    private LessonEntity lesson1;
    private LessonEntity lesson2;
    private Person notIntersectPersons;

    @Before
    public void prepare() throws InAnotherLessonException {
        lesson1 = Generate.simpleLesson();
        {
            Set<InstructorEntity> instructors = new HashSet<>();
            instructors.add(createInstructor());
            instructors.add(createInstructor());
            instructors.add(createInstructor());
            lesson1.setInstructors(instructors);
        }
        lesson1 = getLessonDao().saveLesson(lesson1);

        notIntersectPersons = createInstructor();
        lesson2 = Generate.simpleLesson();
        Set<InstructorEntity> instructors = new HashSet<>();
        instructors.add((InstructorEntity) notIntersectPersons);
        for (InstructorEntity instructor : lesson1.getInstructors()){
            instructors.add(instructor);
        }
        lesson2.setInstructors(instructors);
    }

    /**
     *      |------|
     *  |------|
     */
    @Test(expected = InAnotherLessonException.class)
    public void test1() throws InAnotherLessonException {
        lesson2.setStartDate(addMinutes(lesson1.getStartDate(), -90));
        lesson2.setEndDate(addMinutes(lesson1.getStartDate(), 90));
        lesson2 = getLessonDao().saveLesson(lesson2);
    }

    /**
     *      |------|
     *          |------|
     */
    @Test(expected = InAnotherLessonException.class)
    public void test2() throws InAnotherLessonException {
        lesson2.setStartDate(addMinutes(lesson1.getEndDate(), -90));
        lesson2.setEndDate(addMinutes(lesson1.getEndDate(), 90));
        lesson2 = getLessonDao().saveLesson(lesson2);
    }

    /**
     *      |------|
     *  |--------------|
     */
    @Test(expected = InAnotherLessonException.class)
    public void test3() throws InAnotherLessonException {
        lesson2.setStartDate(addMinutes(lesson1.getStartDate(), -90));
        lesson2.setEndDate(addMinutes(lesson1.getEndDate(), 90));
        lesson2 = getLessonDao().saveLesson(lesson2);
    }

    /**
     *      |------|
     *             |------|
     *  Эта вставка должна пройти
     */
    @Test
    public void test4() throws InAnotherLessonException {
        lesson2.setStartDate(lesson1.getEndDate());
        lesson2.setEndDate(addMinutes(lesson1.getEndDate(), 180));
        lesson2 = getLessonDao().saveLesson(lesson2);
    }

    /**
     *         |------|
     *  |------|
     *  Эта вставка должна пройти
     */
    @Test
    public void test5() throws InAnotherLessonException{
        lesson2.setStartDate(addMinutes(lesson1.getStartDate(), -180));
        lesson2.setEndDate(lesson1.getStartDate());
        lesson2 = getLessonDao().saveLesson(lesson2);
    }

    /**
     *    |---------|
     *       |---|
     */
    @Test(expected = InAnotherLessonException.class)
    public void test6() throws InAnotherLessonException {
        lesson2.setStartDate(addMinutes(lesson1.getStartDate(), 10));
        lesson2.setEndDate(addMinutes(lesson1.getEndDate(), -10));
        lesson2 = getLessonDao().saveLesson(lesson2);
    }

    /**
     * Проверка того, что в ошибке хранятся именно те кто пересекаются с другими уроками
     */
    @Test
    public void checkPersonsInException(){
        lesson2.setStartDate(addMinutes(lesson1.getStartDate(), 10));
        lesson2.setEndDate(addMinutes(lesson1.getEndDate(), -10));
        try {
            lesson2 = getLessonDao().saveLesson(lesson2);
        }catch (InAnotherLessonException e){
            Assert.assertEquals(lesson2.getInstructors().size() - 1, e.getPersons().size());
            e.getPersons().forEach(Person -> Assert.assertNotEquals(notIntersectPersons, Person));
        }
    }

    private static Date addMinutes(Date d, int minutes){
        return DateUtil.addTime(d, Calendar.MINUTE, minutes);
    }
}