package ru.bukan.ssm.domain.model.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bukan.ssm.domain.Dao;
import ru.bukan.ssm.domain.model.customer.CustomerEntity;
import ru.bukan.ssm.domain.model.customer.CustomerEntity_;
import ru.bukan.ssm.domain.util.DateUtil;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
        if (lesson.getCustomers() != null && lesson.getCustomers().size() > 0) {
            for (CustomerEntity customer : lesson.getCustomers()) {
                if (!isFreeCustomer(CustomerEntity.class, customer.getId(), lesson)) {
                    throw new InAnotherLessonException("Один из учеников записан в другом уроке");
                }
            }
        }
        return dao.merge(lesson);
    }

    /**
     * Проверка того что клиент не находится на другом уроке
     */
    // TODO передалать поиск под массив идентфикаторов(что бы обращение к БД было одно)
    // TODO подумать как сделать этот кусок универсальным для клиентов и инструкторов
    private <ENTITY> boolean isFreeCustomer(Class<ENTITY> cl, Long customerId, LessonEntity lesson){
        CriteriaBuilder builder = dao.getEm().getCriteriaBuilder();
        CriteriaQuery<LessonEntity> criteria = builder.createQuery(LessonEntity.class);
        // FROM
        Root<LessonEntity> root = criteria.from(LessonEntity.class);
        //if (cl instanceof )
        Join<LessonEntity, CustomerEntity> customerEntityJoin = root.join(LessonEntity_.customers);

        // params
        ParameterExpression<Long> customerIdParameter      = builder.parameter(Long.class);
        ParameterExpression<Date> lessonStartDateParameter = builder.parameter(Date.class);
        ParameterExpression<Date> lessonEndDateParameter   = builder.parameter(Date.class);

        // WHERE
        Predicate predicate;
        predicate = builder.and(builder.equal(customerEntityJoin.get(CustomerEntity_.id), customerIdParameter));
        if (lesson.getId() != null) {
            predicate = builder.and(predicate, builder.notEqual(root.get(LessonEntity_.id), lesson.getId()));
        }

        Predicate fromDate = builder.lessThanOrEqualTo(root.get(LessonEntity_.startDate), lessonEndDateParameter);
        Predicate toDate = builder.greaterThanOrEqualTo(root.get(LessonEntity_.endDate), lessonStartDateParameter);
        predicate = builder.and(predicate, (builder.and(fromDate, toDate)));

        criteria.where(predicate);
        TypedQuery<LessonEntity> query = dao.getEm().createQuery(criteria);
        // bind params
        query.setParameter(customerIdParameter, customerId);
        query.setParameter(lessonStartDateParameter, DateUtil.addTime(lesson.getStartDate(), Calendar.MILLISECOND, 1));
        query.setParameter(lessonEndDateParameter, DateUtil.addTime(lesson.getEndDate(), Calendar.MILLISECOND, -1));

        List<LessonEntity> resultQuery = query.getResultList();
        boolean result = false;
        if (resultQuery.size() == 0){
            result = true;
        }
        return result;
    }
}