package ru.fb2lib.dao;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.fb2lib.datasets.Book;
import ru.fb2lib.datasets.Document;
import ru.fb2lib.db.HibernateUtil;

import javax.persistence.EntityManager;

import static ru.fb2lib.dao.DaoFactory.getDocumentDao;

/**
 * Created by hav on 17.02.16.
 */
public class DocumentDaoTest {

    private static EntityManager entityManager;

    @BeforeClass
    public static void init() {
        HibernateUtil.setTestEntityManagerFactory();
        entityManager = HibernateUtil.createEntityManager();
        entityManager.getTransaction().begin();
        Document document = new Document();
        document.setDocumentId("13D1B680-685A-49C1-9FDA-A69EE7DEE529");
        entityManager.persist(document);
        Book book = new Book();
        book.setTitle("Test title");
        book.setDocument(document);
        entityManager.persist(book);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testGetDocumentById() throws Exception {
        Document document = getDocumentDao().getDocument(1);
        Assert.assertNotNull(document);
    }

    @Test
    public void testGetDocumentByDocId() throws Exception {
        Document document = getDocumentDao().getDocument("13D1B680-685A-49C1-9FDA-A69EE7DEE529");
        Assert.assertNotNull(document);
    }

    @Test
    public void testInsertDocument() throws Exception {
        // Add new document
        Document document = new Document();
        document.setDocumentId("FE436341-545C-428F-AB62-04B8729797E4");
        getDocumentDao().insertDocument(document);
        Assert.assertNotNull(document.getId());

        // Add already existing document
        document = new Document();
        document.setDocumentId("13D1B680-685A-49C1-9FDA-A69EE7DEE529");
        document = getDocumentDao().insertDocument(document);
        Assert.assertEquals(1, document.getId().longValue());
    }
}