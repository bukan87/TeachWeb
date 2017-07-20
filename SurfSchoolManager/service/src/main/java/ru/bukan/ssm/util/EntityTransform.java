package ru.bukan.ssm.util;

import ru.bukan.ssm.domain.model.customer.CustomerEntity;
import ru.bukan.ssm.domain.model.instructor.InstructorEntity;
import ru.bukan.ssm.domain.model.lesson.LessonEntity;
import ru.bukan.ssm.service.types.lesson.Customer;
import ru.bukan.ssm.service.types.lesson.Instructor;
import ru.bukan.ssm.service.types.lesson.LessonInfo;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

/**
 * Класс транфсформации из Entity в сервисные методы
 *
 * @author by Ilin_ai on 20.07.2017.
 */
public class EntityTransform {

        /*public LessonEntity toEntity(Lesson lesson){
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setId(lesson.getId());
        lessonEntity.setLessonType(lesson.getLessonType());
        lessonEntity.setStartDate(lesson.getStartDate().toGregorianCalendar().getTime());
        if (lesson.getCustomers() != null && lesson.getCustomers().size() > 0) {
            for (Lesson.Customers customer : lesson.getCustomers()) {
                CustomerEntity dstCustomer = new CustomerEntity();
                dstCustomer.setId(customer.getId());
                dstCustomer.setLastName(customer.getLastName());
                dstCustomer.setFirstName(customer.getFirstName());
                dstCustomer.setLastName(customer.getLastName());
                lessonEntity.getCustomers().add(dstCustomer);
            }
        }
        if (lesson.getInstructors() != null && lesson.getInstructors().size() > 0){
            for (Lesson.Instructors instructor : lesson.getInstructors()){
                InstructorEntity dstInstructor = new InstructorEntity();
                dstInstructor.setId(instructor.getId());
                dstInstructor.setLastName(instructor.getLastName());
                dstInstructor.setFirstName(instructor.getFirstName());
                dstInstructor.setLastName(instructor.getLastName());
                lessonEntity.getInstructors().add(dstInstructor);
            }
        }
        return lessonEntity;
    }*/

    public static LessonInfo fromEntity(LessonEntity lessonEntity){

        LessonInfo lesson = new LessonInfo();

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
            lesson.setLessonCustomers(new LessonInfo.LessonCustomers());
            for (CustomerEntity customer : lessonEntity.getCustomers()) {
                Customer dstCustomer = new Customer();
                dstCustomer.setId(customer.getId());
                dstCustomer.setLastName(customer.getLastName());
                dstCustomer.setFirstName(customer.getFirstName());
                dstCustomer.setLastName(customer.getLastName());
                lesson.getLessonCustomers().getCustomer().add(dstCustomer);
            }
        }
        if (lessonEntity.getInstructors() != null && lessonEntity.getInstructors().size() > 0){
            lesson.setLessonInstructors(new LessonInfo.LessonInstructors());
            for (InstructorEntity instructor : lessonEntity.getInstructors()){
                Instructor dstInstructor = new Instructor();
                dstInstructor.setId(instructor.getId());
                dstInstructor.setLastName(instructor.getLastName());
                dstInstructor.setFirstName(instructor.getFirstName());
                dstInstructor.setLastName(instructor.getLastName());
                lesson.getLessonInstructors().getInstructor().add(dstInstructor);
            }
        }
        return lesson;
    }
}
