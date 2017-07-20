package ru.bukan.ssm.domain.SimpleInsert;

import org.junit.Test;
import ru.bukan.ssm.domain.BasicTest;
import ru.bukan.ssm.domain.model.customer.CustomerEntity;
import ru.bukan.ssm.domain.model.lesson.LessonEntity;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author by Ilin_ai on 11.07.2017.
 */
public class MainTest extends BasicTest {

    @Test
    public void createLesson(){
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonType(1);
        Timestamp d = new Timestamp(new Date().getTime());
        lessonEntity.setStartDate(d);
        /*Set<CustomerEntity> customers = new HashSet<>();
        customers.add(createCustomer());*/
        lessonEntity.setCustomers(new HashSet<>());
        lessonEntity.getCustomers().add(createCustomer());
        //lessonEntity.addCustomer(createCustomer());
        getDao().save(lessonEntity);
        //getDao().delete(lessonEntity);
    }

    private CustomerEntity createCustomer(){
        Random rnd = new Random();
        CustomerEntity customer = new CustomerEntity();
        customer.setFirstName("CustomerFamily" + rnd.nextInt());
        customer.setLastName("CustomerName");
        getDao().save(customer);
        return customer;
    }
}