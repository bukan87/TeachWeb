package ru.bukan.ssm.domain.model.lesson;

/**
 * Исключение при нахождении клиента или иснтруктора на другом уроке
 *
 * @author by Ilin_ai on 25.07.2017.
 */
public class InAnotherLessonException extends Exception{
    public InAnotherLessonException(String message) {
        super(message);
    }
}
