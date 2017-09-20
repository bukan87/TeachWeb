package ru.bukan.ssm.domain;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.bukan.ssm.domain.model.customer.CustomerEntity;
import ru.bukan.ssm.domain.model.instructor.InstructorEntity;
import ru.bukan.ssm.domain.model.lesson.LessonDao;
import ru.bukan.ssm.domain.testUtil.Generate;

/**
 * @author by Ilin_ai on 11.07.2017.
 */
@ContextConfiguration(locations = {"classpath:domain-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Component
public abstract class BasicTest {
    @Autowired
    private Dao dao;

    @Autowired
    private LessonDao lessonDao;

    public Dao getDao() {
        return dao;
    }

    public LessonDao getLessonDao(){
        return lessonDao;
    }

    public CustomerEntity createCustomer(){
        // TODO Переписать когда будет ДАО для клиента
        return getDao().merge(Generate.simpleCustomer());
    }

    public InstructorEntity createInstructor(){
        return getDao().merge(Generate.simpleInstructor());
    }
}