package ru.fb2lib.dao;

import ru.fb2lib.datasets.Book;
import ru.fb2lib.datasets.Person;

import java.util.List;

/**
 * Created by hav on 25.01.16.
 */
public interface PersonDao {

    Person getPerson(long id);
    Person getPerson(String name, String middleName, String lastName);

    List<Person> getAllPersons();
    List<Person> getAllAuthors();
    List<Book> getBooksOfAuthor(long id);

    Person insertPerson(Person person);
}
