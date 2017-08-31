package ru.bukan.ssm.domain.model.lesson;

import ru.bukan.ssm.domain.model.Person;

import java.util.List;

/**
 * Исключение при нахождении клиента или иснтруктора на другом уроке
 *
 * @author by Ilin_ai on 25.07.2017.
 */
public class InAnotherLessonException extends Exception{

    private List<Person> persons;

    public InAnotherLessonException(){}

    public InAnotherLessonException(List<Person> persons){
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
