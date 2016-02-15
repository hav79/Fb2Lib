package ru.fb2lib.dao;

import ru.fb2lib.datasets.Document;

/**
 * Created by hav on 03.02.16.
 */
public interface DocumentDao {

    Document getDocument(long id);
    Document getDocument(String docId);
    Document insertDocument(Document document);
}
