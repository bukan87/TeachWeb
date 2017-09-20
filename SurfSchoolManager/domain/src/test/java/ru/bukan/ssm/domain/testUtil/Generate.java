package ru.bukan.ssm.domain.testUtil;

import ru.bukan.ssm.domain.model.customer.CustomerEntity;
import ru.bukan.ssm.domain.model.lesson.LessonEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Генерация различных сущностей
 *
 * @author by Ilin_ai on 25.07.2017.
 */
public class Generate {


    public static CustomerEntity simpleCustomer(){
        Random rnd = new Random();
        CustomerEntity customer = new CustomerEntity();
        customer.setFirstName("CustomerFamily" + rnd.nextInt());
        customer.setLastName("CustomerName");
        return customer;
    }

    /**
     * @return урок без различных перевязок(Клиенты, Интсрукторы и пр.)
     */
    public static LessonEntity simpleLesson(){
        LessonEntity lessonEntity = new LessonEntity();
        // TODO тип занятия нужно генерить из справочника
        lessonEntity.setLessonType(1);
        lessonEntity.setStartDate(lessonStartDate());
        return lessonEntity;
    }

    /**
     * Генерация случайного времени начала урока в промежутке от 5 до 15 часов
     * с интервалом в 15 минут
     */
    public static Date lessonStartDate(){
        Calendar calc = Calendar.getInstance();
        calc.setTime(new Date());
        calc.set(Calendar.HOUR_OF_DAY, 6);
        calc.set(Calendar.MINUTE, 0);
        calc.set(Calendar.SECOND, 0);
        calc.set(Calendar.MILLISECOND, 0);
        calc.add(Calendar.MINUTE, 15 * new Random().nextInt(40));
        return calc.getTime();
    }
}
