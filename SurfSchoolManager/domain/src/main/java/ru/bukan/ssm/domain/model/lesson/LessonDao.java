package ru.bukan.ssm.domain.model.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bukan.ssm.domain.Dao;
import ru.bukan.ssm.domain.model.Person;
import ru.bukan.ssm.domain.model.customer.CustomerEntity;
import ru.bukan.ssm.domain.model.instructor.InstructorEntity;
import ru.bukan.ssm.domain.util.DateUtil;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Дао по работе с уроком
 *
 * @author by Ilin_ai on 21.07.2017.
 */
@Repository
@Transactional
public class LessonDao {

    @Autowired
    private Dao dao;

    /**
     * Создание/изменение данных об уроке
     */
    public LessonEntity saveLesson(LessonEntity lesson) throws InAnotherLessonException{
        if (lesson.getEndDate() == null){
            Calendar calc = Calendar.getInstance();
            calc.setTime(lesson.getStartDate());
            // TODO Сделать время по умолчанию через конфиг
            calc.add(Calendar.HOUR_OF_DAY, 3);
            lesson.setEndDate(calc.getTime());
        }
        if (getIntersectPersons(lesson).size() > 0){
            throw new InAnotherLessonException("Один из учеников записан в другом уроке");
        }
        return dao.merge(lesson);
    }

    /**
     * Определение персон, которые находятся на другом уроке в это же время
     */
    private <ENTITY extends Person> List<ENTITY> getIntersectPersons(Class<ENTITY> cl, LessonEntity lesson){
        CriteriaBuilder builder = dao.getEm().getCriteriaBuilder();
        CriteriaQuery<ENTITY> criteria = builder.createQuery(cl);
        // FROM
        Root<ENTITY> root = criteria.from(cl);
        Join<ENTITY, LessonEntity> lessonJoin = root.join("lessons");

        // params
        ParameterExpression<List> personsIdParameter      = builder.parameter(List.class);
        ParameterExpression<Date> lessonStartDateParameter = builder.parameter(Date.class);
        ParameterExpression<Date> lessonEndDateParameter   = builder.parameter(Date.class);

        List<Long> personsId = new ArrayList<>();

        if (cl == CustomerEntity.class){
            if (lesson.getCustomers() != null) {
                for (Person person : lesson.getCustomers()) {
                    personsId.add(person.getId());
                }
            }
        }else if (cl == InstructorEntity.class){
            if (lesson.getInstructors() != null) {
                for (Person person : lesson.getInstructors()) {
                    personsId.add(person.getId());
                }
            }
        }

        Predicate where = builder.and(root.get("id").in(personsIdParameter));
        if (lesson.getId() != null){
            where = builder.and(where, builder.notEqual(lessonJoin.get(LessonEntity_.id), lesson.getId()));
        }
        where = builder.and(where,
                builder.lessThanOrEqualTo(lessonJoin.get(LessonEntity_.startDate), lessonEndDateParameter),
                builder.greaterThanOrEqualTo(lessonJoin.get(LessonEntity_.endDate), lessonStartDateParameter));
        criteria.where(where);
        TypedQuery<ENTITY> query = dao.getEm().createQuery(criteria);

        query.setParameter(personsIdParameter, personsId);
        query.setParameter(lessonStartDateParameter, DateUtil.addTime(lesson.getStartDate(), Calendar.MILLISECOND, 1));
        query.setParameter(lessonEndDateParameter, DateUtil.addTime(lesson.getEndDate(), Calendar.MILLISECOND, -1));

        return query.getResultList();
    }

    private List<Person> getIntersectPersons(LessonEntity lessonEntity){
        List<Person> personList = new ArrayList<>();
        if (lessonEntity.getCustomers() != null && lessonEntity.getCustomers().size() > 0)
            personList.addAll(getIntersectPersons(CustomerEntity.class, lessonEntity));
        if (lessonEntity.getInstructors() != null && lessonEntity.getInstructors().size() > 0)
            personList.addAll(getIntersectPersons(InstructorEntity.class, lessonEntity));
        return personList;
    }
}