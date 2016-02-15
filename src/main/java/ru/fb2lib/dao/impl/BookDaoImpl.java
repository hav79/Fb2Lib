package ru.fb2lib.dao.impl;

import org.hibernate.Hibernate;
import ru.fb2lib.dao.BookDao;
import ru.fb2lib.datasets.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static ru.fb2lib.db.HibernateUtil.createEntityManager;

/**
 * Created by hav on 07.01.16.
 */
public class BookDaoImpl implements BookDao {

    public BookDaoImpl() {
    }

    @Override
    public Book getBook(long id) {
        EntityManager em = createEntityManager();
        Book res =  em.find(Book.class, id);
        em.close();
        return res;
    }

    @Override
    public Book getBook(String title) {
        Book res = null;
        EntityManager em = createEntityManager();
        CriteriaQuery<Book> criteria = em.getCriteriaBuilder().createQuery(Book.class);
        Root<Book> bookRoot = criteria.from(Book.class);
        criteria.select(bookRoot);
        criteria.where(em.getCriteriaBuilder().equal(bookRoot.get(Book_.title), title));
        if (em.createQuery(criteria).getResultList().size() == 1)
            res = em.createQuery(criteria).getSingleResult();
        em.close();
        return res;
    }

    @Override
    public Book insertBook(Book book) {
        // TODO Надо ли проверять на существование?
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        Book bookInDb = getBook(book.getTitle());
        if (bookInDb == null)
            em.persist(book);
        else
            book = bookInDb;
        em.getTransaction().commit();
        em.close();
        return book;
    }

    @Override
    public void addSequence(Long bookId, Sequence sequence, Long number) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        Book book = em.find(Book.class, bookId);
        if (!em.contains(sequence))
            em.persist(sequence);
        book.addSequence(sequence, number);
        em.getTransaction().commit();
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> res;
        EntityManager em = createEntityManager();
        CriteriaQuery<Book> criteria = em.getCriteriaBuilder().createQuery(Book.class);
        Root<Book> bookRoot = criteria.from(Book.class);
        criteria.select(bookRoot);
        res = em.createQuery(criteria).getResultList();
        em.close();
        return res;
    }

    @Override
    public List<Person> getAuthorsOfBook(long id) {
        EntityManager em = createEntityManager();
        Book book =  em.find(Book.class, id);
        Hibernate.initialize(book.getAuthors());
        List<Person> authors = book.getAuthors();
        em.close();
        return authors;
    }

    @Override
    public List<Genre> getGenresOfBook(long id) {
        EntityManager em = createEntityManager();
        Book book =  em.find(Book.class, id);
        Hibernate.initialize(book.getGenres());
        List<Genre> genres = book.getGenres();
        em.close();
        return genres;
    }

    @Override
    public List<BookSequences> getSequencesOfBook(long id) {
        EntityManager em = createEntityManager();
        Book book = em.find(Book.class, id);
        Hibernate.initialize(book.getSequences());
        List<BookSequences> sequences = book.getSequences();
        em.close();
        return sequences;
    }
}
