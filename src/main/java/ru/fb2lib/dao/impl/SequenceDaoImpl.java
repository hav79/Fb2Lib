package ru.fb2lib.dao.impl;

import ru.fb2lib.dao.SequenceDao;
import ru.fb2lib.datasets.Sequence;
import ru.fb2lib.datasets.Sequence_;
import ru.fb2lib.db.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by hav on 26.01.16.
 */
public class SequenceDaoImpl implements SequenceDao {

    @Override
    public Sequence getSequence(long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        Sequence res = em.find(Sequence.class, id);
        em.close();
        return res;
    }

    @Override
    public Sequence getSequence(String name, String srcName) {
        Sequence res = null;
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        CriteriaQuery<Sequence> criteria = em.getCriteriaBuilder().createQuery(Sequence.class);
        Root<Sequence> sequenceRoot = criteria.from(Sequence.class);
        criteria.select(sequenceRoot);
        criteria.where(em.getCriteriaBuilder().and(
                em.getCriteriaBuilder().equal(sequenceRoot.get(Sequence_.name), name),
                em.getCriteriaBuilder().equal(sequenceRoot.get(Sequence_.srcName), srcName)
        ));
        if (em.createQuery(criteria).getResultList().size() == 1)
            return em.createQuery(criteria).getSingleResult();
        em.close();
        return res;
    }

    @Override
    public Sequence insertSequence(Sequence sequence) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Sequence sequenceInDb = getSequence(sequence.getName(), sequence.getSrcName());
        if (sequenceInDb == null)
            em.persist(sequence);
        else
            sequence = sequenceInDb;
        em.getTransaction().commit();
        em.close();
        return sequence;
    }
}
