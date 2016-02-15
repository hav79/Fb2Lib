package ru.fb2lib.dao.impl;

import ru.fb2lib.dao.EditionDao;
import ru.fb2lib.datasets.Book;
import ru.fb2lib.datasets.Edition;
import ru.fb2lib.datasets.Edition_;
import ru.fb2lib.db.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hav on 25.01.16.
 */
public class EditionDaoImpl implements EditionDao {

    public EditionDaoImpl() {
    }

    @Override
    public Edition getEdition(long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        Edition res = em.find(Edition.class, id);
        em.close();
        return res;
    }

    @Override
    public Edition getEdition(String isbn) {
        Edition res = null;
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        CriteriaQuery<Edition> criteria = em.getCriteriaBuilder().createQuery(Edition.class);
        Root<Edition> editionRoot = criteria.from(Edition.class);
        criteria.select(editionRoot);
        criteria.where(em.getCriteriaBuilder().equal(editionRoot.get(Edition_.isbn), isbn));
        if (em.createQuery(criteria).getResultList().size() == 1)
            res = em.createQuery(criteria).getSingleResult();
        em.close();
        return res;
    }

    @Override
    public List<Book> getBooksFromEdition(long id) {
        return new ArrayList<>(getEdition(id).getBooks());
    }

    @Override
    public Edition insertEdition(Edition edition) {
        if (edition.getIsbn().equals(""))
            return null;
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Edition editionInDb = getEdition(edition.getIsbn());
        if (editionInDb == null)
            em.persist(edition);
        else
            edition = editionInDb;
        em.getTransaction().commit();
        em.close();
        return edition;
    }
}
