package ru.fb2lib.dao;

import ru.fb2lib.datasets.*;

import java.util.List;

/**
 * Created by hav on 25.01.16.
 */
public interface BookDao {

    Book getBook(long id);
    List<Book> getAllBooks();
    List<Person> getAuthorsOfBook(long id);
    List<Genre> getGenresOfBook(long id);

    Book getBook(String title);

    Book insertBook(Book book);
    void addSequence(Long bookId, Sequence sequence, Long number);

    List<BookSequences> getSequencesOfBook(long id);
}
