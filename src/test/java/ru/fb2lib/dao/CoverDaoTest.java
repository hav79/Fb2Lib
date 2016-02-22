package ru.fb2lib.dao;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.fb2lib.datasets.Cover;
import ru.fb2lib.db.HibernateUtil;

import javax.persistence.EntityManager;

import static ru.fb2lib.dao.DaoFactory.getCoverDao;

/**
 * Created by hav on 18.02.16.
 */
public class CoverDaoTest {

    private static EntityManager entityManager;

    @BeforeClass
    public static void init() {
        HibernateUtil.setTestEntityManagerFactory();
        entityManager = HibernateUtil.createEntityManager();

        entityManager.getTransaction().begin();
        Cover cover = new Cover("cover.ipg", "/9j/4AAQSkZJRgABAQEAWgBaAAD/2wBDAAYE", "image/jpeg");
        entityManager.persist(cover);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testGetCover() throws Exception {
        Cover cover = getCoverDao().getCover(1);
        Assert.assertNotNull(cover);
    }

    @Test
    public void testInsertCover() throws Exception {
        Cover cover = new Cover("cover.ipg", "/9j/4AAQSkZJRgABAQAAAQABAAD//gA7Q1JFQVR", "image/jpeg");
        getCoverDao().insertCover(cover);
        Assert.assertNotNull(cover.getId());
    }
}