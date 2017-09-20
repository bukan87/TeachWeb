package ru.bukan.ssm.domain.lesson.createLesson;

import org.junit.Before;
import org.junit.Test;
import ru.bukan.ssm.domain.BasicTest;
import ru.bukan.ssm.domain.model.customer.CustomerEntity;
import ru.bukan.ssm.domain.model.lesson.InAnotherLessonException;
import ru.bukan.ssm.domain.model.lesson.LessonEntity;
import ru.bukan.ssm.domain.testUtil.Generate;
import ru.bukan.ssm.domain.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Создание урока с клиентом, находящемся на другом уроке в это же время
 * |-------| - продолжительность урока
 * верхний урок это первый(уже созданный урок)
 * нижний второй
 * @author by Ilin_ai on 25.07.2017.
 */
public class LessonWithCustomerInAnotherLesson extends BasicTest{

    private LessonEntity lesson1;
    private LessonEntity lesson2;

    @Before
    public void prepare() throws InAnotherLessonException{
        lesson1 = Generate.simpleLesson();
        {
            Set<CustomerEntity> customers = new HashSet<>();
            customers.add(createCustomer());
            customers.add(createCustomer());
            customers.add(createCustomer());
            lesson1.setCustomers(customers);
        }
        lesson1 = getLessonDao().saveLesson(lesson1);


        lesson2 = Generate.simpleLesson();
        Set<CustomerEntity> customers = new HashSet<>();
        customers.add(createCustomer());
        for (CustomerEntity customer : lesson1.getCustomers()){
            customers.add(customer);
        }
        lesson2.setCustomers(customers);
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

    private static Date addMinutes(Date d, int minutes){
        return DateUtil.addTime(d, Calendar.MINUTE, minutes);
    }
}
