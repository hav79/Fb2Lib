package ru.fb2lib.db;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.fb2lib.dao.DaoFactory;
import ru.fb2lib.datasets.*;
import ru.fb2lib.parser.Fb2Parser;
import ru.fb2lib.parser.Parser;

import javax.xml.xpath.XPathExpressionException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by hav on 05.01.16.
 */
public class Library implements Observable {

    private Logger log = Logger.getLogger(Library.class.getName());

    private List<InvalidationListener> listeners = new LinkedList<>();
//    private List<Person> addedPersons = new ArrayList<>();
//    private ListProperty<Person> authors = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ObservableList<Person> authors = FXCollections.observableArrayList();

    public Library() {
//        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
//        org.jboss.logging.Logger.getLogger("org.hibernate");
    }

    public long addPerson(String name, String middleName, String lastName) {
        log.info("Add person: " + name + " " + middleName + "" + lastName);
        Long id = DaoFactory
                .getInstance()
                .getPersonDao()
                .insertPerson(new Person(name, middleName, lastName)).getId();
        notifyObservers();
        return id;
    }

    public Person getPerson(long id) {
        return DaoFactory.getInstance().getPersonDao().getPerson(id);
    }

    public long addBookFromFile(String filename) {
        log.info("Add book from file " + filename);
        Book book = new Book(filename);
        Parser parser = new Fb2Parser(filename);
        try {
            book.setGenres(parser.parseGenres());
            for (Person p: parser.parseAuthors()) {
                Person inDb = DaoFactory.getInstance().getPersonDao().insertPerson(p);
                book.addAuthor(inDb);
                if (!authors.contains(inDb))
//                    authors.retainAll(inDb);
                    authors.add(inDb);
            }
            book.setTitle(parser.parseTitle());
            book.setSrcTitle(parser.parseSrcTitle());
            book.setDate(parser.parseYear());
            book.setLang(parser.parseLang());
            book.setSrcLang(parser.parseSrcLang());
            for (Person p : parser.parseTranslators())
                book.addTranslator(DaoFactory.getInstance().getPersonDao().insertPerson(p));
            for (BookSequences bs : parser.parseSequences())
                book.addSequence(DaoFactory.getInstance().getSequenceDao().insertSequence(bs.getSequence()),
                        bs.getNumber());
            book.setKeywords(parser.parseKeywords());
            book.setAnnotation(parser.parseAnnotation());
            book.setCover(DaoFactory.getInstance().getCoverDao().insertCover(parser.parseCover()));
//            book.setSrcCover(DaoFactory.getInstance().getCoverDao().insertCover(parser.parseSrcCover()));
            
            Document doc = parser.parseDocumentInfo();
            for (Person p : parser.parseDocAuthors()) {
                doc.addAuthor(DaoFactory.getInstance().getPersonDao().insertPerson(p));
            }
            book.setDocument(DaoFactory.getInstance().getDocumentDao().insertDocument(doc));

            Edition edition = parser.parseEdition();
            book.setEdition(DaoFactory.getInstance().getEditionDao().insertEdition(edition));
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        Long bookId = DaoFactory.getInstance().getBookDao().insertBook(book).getId();

        notifyObservers();
        return bookId;
    }

    public Book getBook(long id) {
        return DaoFactory.getInstance().getBookDao().getBook(id);
    }

    public List<Person> getAllPersons() {
        return DaoFactory.getInstance().getPersonDao().getAllPersons();
    }

    public ObservableList<Person> getAuthors() {
        return authors;
    }
//
//    public ListProperty<Person> authorsProperty() {
//        return authors;
//    }

    public List<Book> getAllBooks() {
        return DaoFactory.getInstance().getBookDao().getAllBooks();
    }

    public List<Person> getAllAuthors() {
        return DaoFactory.getInstance().getPersonDao().getAllAuthors();
    }

    public List<Book> getBooksOfAuthor(long id) {
        return DaoFactory.getInstance().getPersonDao().getBooksOfAuthor(id);
    }

    public List<Person> getAuthorsOfBook(long id) {
        return DaoFactory.getInstance().getBookDao().getAuthorsOfBook(id);
    }

    public List<Genre> getGenresOfBook(long id) {
        return DaoFactory.getInstance().getBookDao().getGenresOfBook(id);
    }

    public List<BookSequences> getSequencesOfBook(long id) {
        return DaoFactory.getInstance().getBookDao().getSequencesOfBook(id);
    }

//    public List<Person> getAddedPersons() {
//        return addedPersons;
//    }

    @Override
    public void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }

    private void notifyObservers() {
        for (InvalidationListener listener: listeners) {
            listener.invalidated(this);
        }
//        addedPersons.clear();
    }
}
