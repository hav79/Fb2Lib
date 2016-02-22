package ru.fb2lib.dao.impl;

import ru.fb2lib.dao.DocumentDao;
import ru.fb2lib.datasets.Document;
import ru.fb2lib.datasets.Document_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static ru.fb2lib.db.HibernateUtil.createEntityManager;

/**
 * Created by hav on 03.02.16.
 */
public class DocumentDaoImpl implements DocumentDao {

    public DocumentDaoImpl() {
    }

    @Override
    public Document getDocument(long id) {
        EntityManager em = createEntityManager();
        Document res = em.find(Document.class, id);
        em.close();
        return res;
    }

    @Override
    public Document getDocument(String docId) {
        Document res = null;
        EntityManager em = createEntityManager();
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
        if (document.getDocumentId().equals(""))
            return null;
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        Document docInDb = getDocument(document.getDocumentId());
        if (docInDb == null)
            em.persist(document);
        else
            document = docInDb;
        em.getTransaction().commit();
        em.close();
        return document;
    }
}
