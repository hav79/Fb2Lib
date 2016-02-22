package ru.fb2lib.dao.impl;

import ru.fb2lib.dao.CoverDao;
import ru.fb2lib.datasets.Cover;

import javax.persistence.EntityManager;

import static ru.fb2lib.db.HibernateUtil.createEntityManager;

/**
 * Created by hav on 09.02.16.
 */
public class CoverDaoImpl implements CoverDao {

    @Override
    public Cover getCover(long id) {
        EntityManager em = createEntityManager();
        Cover res = em.find(Cover.class, id);
        em.close();
        return res;
    }

    @Override
    public Cover insertCover(Cover cover) {
        //TODO проверка на существование
        if (cover != null) {
            EntityManager em = createEntityManager();
            em.getTransaction().begin();
            em.persist(cover);
            em.getTransaction().commit();
            em.close();
        }
        return cover;
    }
}
