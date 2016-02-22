package ru.fb2lib.dao;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.fb2lib.datasets.Sequence;
import ru.fb2lib.db.HibernateUtil;

import javax.persistence.EntityManager;

import static ru.fb2lib.dao.DaoFactory.getSequenceDao;

/**
 * Created by hav on 18.02.16.
 */
public class SequenceDaoTest {

    private static EntityManager entityManager;

    @BeforeClass
    public static void init() {
        HibernateUtil.setTestEntityManagerFactory();
        entityManager = HibernateUtil.createEntityManager();
        entityManager.getTransaction().begin();
        Sequence sequence = new Sequence("Test sequence");
        sequence.setSrcName("Test original name");
        entityManager.persist(sequence);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testGetSequenceById() throws Exception {
        Sequence sequence = getSequenceDao().getSequence(1);
        Assert.assertNotNull(sequence);
    }

    @Test
    public void testGetSequenceByName() throws Exception {
        Sequence sequence = getSequenceDao().getSequence("Test sequence", "Test original name");
        Assert.assertNotNull(sequence);
    }

    @Test
    public void testInsertSequence() throws Exception {
        // Add new sequence
        Sequence sequence = new Sequence("Test sequence1");
        sequence.setSrcName("Test original name1");
        getSequenceDao().insertSequence(sequence);
        Assert.assertNotNull(sequence.getId());

        // Add already existing sequence
        sequence = new Sequence("Test sequence");
        sequence.setSrcName("Test original name");
        sequence = getSequenceDao().insertSequence(sequence);
        Assert.assertEquals(1, sequence.getId().longValue());
    }
}