package ru.fb2lib.dao.impl;

import org.hibernate.Hibernate;
import ru.fb2lib.dao.PersonDao;
import ru.fb2lib.datasets.Book;
import ru.fb2lib.datasets.Person;
import ru.fb2lib.datasets.Person_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ru.fb2lib.db.HibernateUtil.createEntityManager;

/**
 * Created by hav on 05.01.16.
 */
public class PersonDaoImpl implements PersonDao {

    public PersonDaoImpl() {
    }

    @Override
    public Person getPerson(long id) {
        EntityManager em = createEntityManager();
        Person res = em.find(Person.class, id);
        em.close();
        return res;
    }

    @Override
    public Person getPerson(String name, String middleName, String lastName) {
        Person res = null;
        EntityManager em = createEntityManager();
        CriteriaQuery<Person> criteria = em.getCriteriaBuilder().createQuery(Person.class);
        Root<Person> personRoot = criteria.from(Person.class);
        criteria.select(personRoot);
        criteria.where(em.getCriteriaBuilder().and(
                em.getCriteriaBuilder().equal(personRoot.get(Person_.firstName), name),
//                em.getCriteriaBuilder().equal(personRoot.get(Person_.middleName), middleName),
                em.getCriteriaBuilder().equal(personRoot.get(Person_.lastName), lastName)
        ));
        if (em.createQuery(criteria).getResultList().size() == 1)
            res = em.createQuery(criteria).getSingleResult();
        em.close();
        return res;
    }

    @Override
    public Person insertPerson(Person person) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        Person personInDb = getPerson(person.getFirstName(), person.getMiddleName(), person.getLastName());
        if (personInDb == null)
            em.persist(person);
        else
            person = personInDb;
        em.getTransaction().commit();
        em.close();
        return person;
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> res;
        EntityManager em = createEntityManager();
        CriteriaQuery<Person> criteria = em.getCriteriaBuilder().createQuery(Person.class);
        Root<Person> personRoot = criteria.from(Person.class);
        criteria.select(personRoot);
        res = em.createQuery(criteria).getResultList();
        em.close();
        return res;
    }

    @Override
    public List<Person> getAllAuthors() {
        List<Person> authors = new ArrayList<>();
        EntityManager em = createEntityManager();
        CriteriaQuery<Person> criteria = em.getCriteriaBuilder().createQuery(Person.class);
        Root<Person> personRoot = criteria.from(Person.class);
        criteria.select(personRoot);
        criteria.where(em.getCriteriaBuilder().isNotEmpty(personRoot.get(Person_.booksWrited)));
        authors.addAll(em.createQuery(criteria).getResultList());
        authors.forEach(Hibernate::initialize);
        em.close();
        return authors;
    }

    @Override
    public List<Book> getBooksOfAuthor(long id) {
        EntityManager em = createEntityManager();
        Person author = em.find(Person.class, id);
        Hibernate.initialize(author.getBooksWrited());
        Set<Book> books = author.getBooksWrited();
        em.close();
        //TODO list or set?
        return new ArrayList<>(books);
    }
}
