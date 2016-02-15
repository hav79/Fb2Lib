package ru.fb2lib.dao;

import ru.fb2lib.datasets.Book;
import ru.fb2lib.datasets.Edition;

import java.util.List;

/**
 * Created by hav on 24.01.16.
 */
public interface EditionDao {
    Edition getEdition(long id);
    Edition getEdition(String isbn);
    List<Book> getBooksFromEdition(long id);
    Edition insertEdition(Edition edition);
}
