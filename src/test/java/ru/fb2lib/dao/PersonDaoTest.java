package ru.fb2lib.dao;

import org.junit.*;
import ru.fb2lib.datasets.Book;
import ru.fb2lib.datasets.Person;
import ru.fb2lib.db.HibernateUtil;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static ru.fb2lib.dao.DaoFactory.getPersonDao;

/**
 * Created by hav on 15.02.16.
 */
public class PersonDaoTest {

    private static EntityManager entityManager;

    @BeforeClass
    public static void init() {
        HibernateUtil.setTestEntityManagerFactory();
        entityManager = HibernateUtil.createEntityManager();
        entityManager.getTransaction().begin();
        List<Person> authors = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Person person = new Person("Ivan" + i, "Ivanovich" + i, "Ivanov" + i);
            entityManager.persist(person);
            if (i != 2)
                authors.add(person);
        }
        for (int i = 0; i < 3; i++) {
            Book book = new Book();
            book.setTitle("Test title " + i);
            book.setAuthors(authors);
            entityManager.persist(book);
        }
        entityManager.getTransaction().commit();
    }

    @Test
    public void testGetPersonById() throws Exception {
        Person person = getPersonDao().getPerson(1);
        Assert.assertNotNull(person);
    }

    @Test
    public void testGetPersonByName() throws Exception {
        Person person = getPersonDao().getPerson("Ivan1", "Ivanovich1", "Ivanov1");
        Assert.assertNotNull("Not return person", person);
    }

    @Test
    public void testGetAllPersons() throws Exception {
        List<Person> persons = getPersonDao().getAllPersons();
        Assert.assertEquals(3, persons.size());
    }

    @Test
    public void testGetAllAuthors() throws Exception {
        List<Person> authors = getPersonDao().getAllAuthors();
        Assert.assertEquals(2, authors.size());
    }

    @Test
    public void testGetBooksOfAuthor() throws Exception {
        List<Book> books = getPersonDao().getBooksOfAuthor(1);
        Assert.assertEquals(3, books.size());
    }

    @Test
    public void testInsertPerson() throws Exception {
        // Add new person
        Person person = new Person("Petr", "Petrovich", "Petrov");
        getPersonDao().insertPerson(person);
        Assert.assertNotNull(person.getId());

        // Add already existing person
        person = getPersonDao().insertPerson(new Person("Ivan" + 0, "Ivanovich" + 0, "Ivanov" + 0));
        Assert.assertEquals(1, person.getId().longValue());
    }
}