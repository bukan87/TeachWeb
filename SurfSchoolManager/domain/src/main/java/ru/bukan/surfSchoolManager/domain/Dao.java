package ru.bukan.surfSchoolManager.domain;

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
}
