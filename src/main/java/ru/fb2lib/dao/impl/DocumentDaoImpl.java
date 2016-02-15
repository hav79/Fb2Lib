package ru.fb2lib.dao.impl;

import ru.fb2lib.dao.DocumentDao;
import ru.fb2lib.datasets.Document;
import ru.fb2lib.datasets.Document_;
import ru.fb2lib.db.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by hav on 03.02.16.
 */
public class DocumentDaoImpl implements DocumentDao {

    public DocumentDaoImpl() {
    }

    @Override
    public Document getDocument(long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        Document res = em.find(Document.class, id);
        em.close();
        return res;
    }

    @Override
    public Document getDocument(String docId) {
        Document res = null;
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        CriteriaQuery<Document> criteria = em.getCriteriaBuilder().createQuery(Document.class);
        Root<Document> documentRoot = criteria.from(Document.class);
        criteria.select(documentRoot);
        criteria.where(em.getCriteriaBuilder().equal(documentRoot.get(Document_.documentId), docId));
        if (em.createQuery(criteria).getResultList().size() == 1)
            res =  em.createQuery(criteria).getSingleResult();
        em.close();
        return res;
    }

    @Override
    public Document insertDocument(Document document) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(document);
        em.getTransaction().commit();
        em.close();
        return document;
    }
}
