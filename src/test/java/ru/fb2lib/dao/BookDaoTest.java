package ru.fb2lib.dao;

import org.junit.*;
import ru.fb2lib.datasets.*;
import ru.fb2lib.db.HibernateUtil;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static ru.fb2lib.dao.DaoFactory.getBookDao;

/**
 * Created by hav on 15.02.16.
 */
public class BookDaoTest {

    private static  EntityManager entityManager;

    @BeforeClass
    public static void init() {
        HibernateUtil.setTestEntityManagerFactory();
        entityManager = HibernateUtil.createEntityManager();

        entityManager.getTransaction().begin();
        List<Person> authors = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Person person = new Person("Ivan" + i, "Ivanovich" + i, "Ivanov" + i);
            entityManager.persist(person);
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
    public void testGetBook() throws Exception {
        Book book = getBookDao().getBook(1);
        Assert.assertNotNull(book);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = getBookDao().getAllBooks();
        Assert.assertEquals(3, books.size());
    }

    @Test
    public void testGetAuthorsOfBook() throws Exception {
        List<Person> authors = getBookDao().getAuthorsOfBook(1);
        Assert.assertEquals(3, authors.size());
    }

    @Test
    public void testGetGenresOfBook() throws Exception {
        List<Genre> genres = getBookDao().getGenresOfBook(1);
        Assert.assertEquals(3, genres.size());
    }


    @Test
    public void testInsertBook() throws Exception {
        // Add new book
        Book book = new Book();
        book.setTitle("Test book");
        getBookDao().insertBook(book);
        Assert.assertNotNull(book.getId());

        // Add already existing book
        book = new Book();
        book.setTitle("Test title 0");
        book = getBookDao().insertBook(book);
        Assert.assertEquals(1, book.getId().longValue());
    }

    @Test
    public void testAddSequence() throws Exception {
        Book book = new Book();
        book.setTitle("Test book");
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
        getBookDao().addSequence(book.getId(), new Sequence("Test sequence 3"), 3L);
        entityManager.refresh(book);
        Assert.assertEquals(1, book.getSequences().size());
    }

    @Test
    public void testGetSequencesOfBook() throws Exception {
        List<BookSequences> sequences = getBookDao().getSequencesOfBook(1);
        Assert.assertEquals(2, sequences.size());
    }
}