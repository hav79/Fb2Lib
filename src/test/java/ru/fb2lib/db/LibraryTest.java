package ru.fb2lib.db;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import ru.fb2lib.datasets.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by hav on 22.02.16.
 */
public class LibraryTest {

    private static EntityManager entityManager;
    private Library library = new Library();

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
            book.addGenre(Genre.sf);
            book.addGenre(Genre.sf_humor);
            book.addGenre(Genre.sf_detective);
            entityManager.persist(book);
            Sequence sequence1 = new Sequence("Test sequence 1");
            Sequence sequence2 = new Sequence("Test sequence 1");
            entityManager.persist(sequence1);
            entityManager.persist(sequence2);
            book.addSequence(sequence1, 1L);
            book.addSequence(sequence2, 2L);
        }
        entityManager.getTransaction().commit();
    }

    @Test
    public void testAddPerson() throws Exception {
        // Add new person
        Long personId = library.addPerson("Ivan", "Ivanovich", "Ivanov");
        Assert.assertNotNull(personId);
        Assert.assertEquals(4, personId.longValue());

        // Add already existing person
        personId = library.addPerson("Ivan0", "Ivanovich0", "Ivanov0");
        Assert.assertEquals(1, personId.longValue());
    }

    @Test
    public void testGetPerson() throws Exception {
        Person person = library.getPerson(1);
        Assert.assertNotNull(person);
    }

    @Ignore
    @Test
    public void testAddBookFromFile() throws Exception {
        Assert.fail("Not ready");
    }

    @Test
    public void testGetBook() throws Exception {
        Book book = library.getBook(1);
        Assert.assertNotNull(book);
    }

    @Test
    public void testGetAllPersons() throws Exception {
        List<Person> persons = library.getAllPersons();
        Assert.assertEquals(3, persons.size());
    }

    @Ignore
    @Test
    public void testGetAuthors() throws Exception {
        Assert.fail("Not ready");
    }

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = library.getAllBooks();
        Assert.assertEquals(3, books.size());
    }

    @Test
    public void testGetAllAuthors() throws Exception {
        List<Person> authors = library.getAllAuthors();
        Assert.assertEquals(2, authors.size());
    }

    @Test
    public void testGetBooksOfAuthor() throws Exception {
        List<Book> books = library.getBooksOfAuthor(1);
        Assert.assertEquals(3, books.size());
    }

    @Test
    public void testGetAuthorsOfBook() throws Exception {
        List<Person> authors = library.getAuthorsOfBook(1);
        Assert.assertEquals(2, authors.size());
    }

    @Test
    public void testGetGenresOfBook() throws Exception {
        List<Genre> genres = library.getGenresOfBook(1);
        Assert.assertEquals(3, genres.size());
    }

    @Test
    public void testGetSequencesOfBook() throws Exception {
        List<BookSequences> sequences = library.getSequencesOfBook(1);
        Assert.assertEquals(2, sequences.size());
    }
}