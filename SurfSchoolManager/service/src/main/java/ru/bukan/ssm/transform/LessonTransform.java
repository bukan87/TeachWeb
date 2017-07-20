package ru.bukan.ssm.transform;

import ru.bukan.ssm.domain.model.customer.CustomerEntity;
import ru.bukan.ssm.domain.model.instructor.InstructorEntity;
import ru.bukan.ssm.domain.model.lesson.LessonEntity;
import ru.bukan.ssm.service.types.lesson.Customer;
import ru.bukan.ssm.service.types.lesson.Instructor;
import ru.bukan.ssm.service.types.lesson.Lesson;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

/**
 * @author by Ilin_ai on 20.07.2017.
 */
public class LessonTransform {
    public LessonEntity toEntity(Lesson lesson){
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setId(lesson.getId());
        lessonEntity.setLessonType(lesson.getLessonType());
        lessonEntity.setStartDate(lesson.getStartDate().toGregorianCalendar().getTime());
        if (lesson.getCustomers() != null && lesson.getCustomers().size() > 0) {
            for (Customer customer : lesson.getCustomers()) {
                CustomerEntity dstCustomer = new CustomerEntity();
                dstCustomer.setId(customer.getId());
                dstCustomer.setLastName(customer.getLastName());
                dstCustomer.setFirstName(customer.getFirstName());
                dstCustomer.setLastName(customer.getLastName());
                lessonEntity.getCustomers().add(dstCustomer);
            }
        }
        if (lesson.getInstructors() != null && lesson.getInstructors().size() > 0){
            for (Instructor instructor : lesson.getInstructors()){
                InstructorEntity dstInstructor = new InstructorEntity();
                dstInstructor.setId(instructor.getId());
                dstInstructor.setLastName(instructor.getLastName());
                dstInstructor.setFirstName(instructor.getFirstName());
                dstInstructor.setLastName(instructor.getLastName());
                lessonEntity.getInstructors().add(dstInstructor);
            }
        }
        return lessonEntity;
    }

    public Lesson fromEntity(LessonEntity lessonEntity){
        Lesson lesson = new Lesson();
        lesson.setId(lessonEntity.getId());
        lesson.setLessonType(lessonEntity.getLessonType());
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(lessonEntity.getStartDate());
        try {
            XMLGregorianCalendar gregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            lesson.setStartDate(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        if (lessonEntity.getCustomers() != null && lessonEntity.getCustomers().size() > 0) {
            for (CustomerEntity customer : lessonEntity.getCustomers()) {
                Customer dstCustomer = new Customer();
                dstCustomer.setId(customer.getId());
                dstCustomer.setLastName(customer.getLastName());
                dstCustomer.setFirstName(customer.getFirstName());
                dstCustomer.setLastName(customer.getLastName());
                lesson.getCustomers().add(dstCustomer);
            }
        }
        if (lessonEntity.getInstructors() != null && lessonEntity.getInstructors().size() > 0){
            for (InstructorEntity instructor : lessonEntity.getInstructors()){
                Instructor dstInstructor = new Instructor();
                dstInstructor.setId(instructor.getId());
                dstInstructor.setLastName(instructor.getLastName());
                dstInstructor.setFirstName(instructor.getFirstName());
                dstInstructor.setLastName(instructor.getLastName());
                lesson.getInstructors().add(dstInstructor);
            }
        }
        return lesson;
    }
}
