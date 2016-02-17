package ru.fb2lib.dao;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.fb2lib.datasets.Book;
import ru.fb2lib.datasets.Edition;
import ru.fb2lib.db.HibernateUtil;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hav on 15.02.16.
 */
public class EditionDaoTest {

    private static EntityManager entityManager;

    @BeforeClass
    public static void init() {
        HibernateUtil.setTestEntityManagerFactory();
        entityManager = HibernateUtil.createEntityManager();
        entityManager.getTransaction().begin();
        List<Edition> editions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Edition edition = new Edition();
            edition.setIsbn("5-04-003603-5" + i);
            edition.setBookName("Test edition" + i);
            entityManager.persist(edition);
            editions.add(edition);
        }
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Book book = new Book();
            book.setTitle("Test title " + i);
            entityManager.persist(book);
            books.add(book);
        }
        books.get(0).setEdition(editions.get(0));
        books.get(1).setEdition(editions.get(0));
        books.get(2).setEdition(editions.get(1));
        entityManager.getTransaction().commit();
    }

    @Test
    public void testGetEditionById() throws Exception {
        Edition edition = DaoFactory.getEditionDao().getEdition(1);
        Assert.assertNotNull(edition);
    }

    @Test
    public void testGetEditionByIsbn() throws Exception {
        Edition edition = DaoFactory.getEditionDao().getEdition("5-04-003603-50");
        Assert.assertNotNull(edition);
    }

    @Test
    public void testGetBooksFromEdition() throws Exception {
        List<Book> books = DaoFactory.getEditionDao().getBooksFromEdition(1);
        Assert.assertEquals(2, books.size());
        books = DaoFactory.getEditionDao().getBooksFromEdition(3);
        Assert.assertEquals(0, books.size());
    }

    @Test
    public void testInsertEdition() throws Exception {
        Edition edition = new Edition();
        edition.setIsbn("5-04-003603-555");
        DaoFactory.getEditionDao().insertEdition(edition);
        Assert.assertNotNull(edition.getId());
    }
}