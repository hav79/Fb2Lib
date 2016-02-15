package ru.fb2lib.dao;

import ru.fb2lib.dao.impl.*;

/**
 * Created by hav on 25.01.16.
 */
public class DaoFactory {
    private static DaoFactory instance = new DaoFactory();

    private static PersonDao personDao = null;
    private static BookDao bookDao = null;
    private static EditionDao editionDao = null;
    private static SequenceDao sequenceDao = null;
    private static DocumentDao documentDao = null;
    private static CoverDao coverDao = null;

    public static DaoFactory getInstance() {
        if (instance == null)
            instance = new DaoFactory();
        return instance;
    }

    private DaoFactory() {
    }

    public static CoverDao getCoverDao() {
        if (coverDao == null)
            coverDao = new CoverDaoImpl();
        return coverDao;
    }

    public static PersonDao getPersonDao() {
        if (personDao == null)
            personDao = new PersonDaoImpl();
        return personDao;
    }

    public static BookDao getBookDao() {
        if (bookDao == null)
            bookDao = new BookDaoImpl();
        return bookDao;
    }

    public static EditionDao getEditionDao() {
        if (editionDao == null)
            editionDao = new EditionDaoImpl();
        return editionDao;
    }

    public static SequenceDao getSequenceDao() {
        if (sequenceDao == null)
            sequenceDao = new SequenceDaoImpl();
        return sequenceDao;
    }

    public static DocumentDao getDocumentDao() {
        if (documentDao == null)
            documentDao = new DocumentDaoImpl();
        return documentDao;
    }
}
