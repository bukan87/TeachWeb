package ru.bukan.ssm.domain;

import javax.persistence.EntityManager;

/**
 * Базовый интерфейс по работе с БД
 * @author by Ilin_ai on 11.07.2017.
 */
public interface Dao {
    /**
     * Создание/изменение записи
     */
    <E> void save(E t);

    /**
     * Удаление записи
     */
    <E> void delete(E t);
    /**
     * Select object by object id/key
     * @param <E>
     * @param klazz
     * @param key
     * @return
     */
    <E> E getByKey(Class<E> klazz, Object key);

    <ENTITY> ENTITY merge(ENTITY e) ;

    EntityManager getEm();
}
