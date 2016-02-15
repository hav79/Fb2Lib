package ru.fb2lib.datasets;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by hav on 18.01.16.
 */

@Entity
@Table(name = "editions")
public class Edition extends BaseDataSet {

    private static final long serialVersionUID = -2156787454878213746L;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "city")
    private String city;

    @Column(name = "year")
    private String year;

    @Column(name = "isbn")
    private String isbn;

//    @Embedded
//    @ElementCollection
//    @CollectionTable(name = "book_isbn")
//    private List<ISBN> isbn = new ArrayList<>();
//    @OneToMany(mappedBy = "edition")
//    private Set<ISBN> isbnSet;

    @Column(name = "sequence_name")
    private String sequenceName;

    @Column(name = "sequence_number")
    private Integer sequenceNumber;

    @OneToMany(mappedBy = "edition")
    private Set<Book> books;

    public Edition() {
        super();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


//    public List<ISBN> getIsbn() {
//        return isbn;
//    }
//
//    public void setIsbn(List<ISBN> isbn) {
//        this.isbn = isbn;
//    }
//
//    public void addIsbn(ISBN isbn) {
//        this.isbn.add(isbn);
//    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edition)) return false;
        if (!super.equals(o)) return false;

        Edition edition = (Edition) o;

        if (bookName != null ? !bookName.equals(edition.bookName) : edition.bookName != null) return false;
        if (publisher != null ? !publisher.equals(edition.publisher) : edition.publisher != null) return false;
        if (city != null ? !city.equals(edition.city) : edition.city != null) return false;
        if (year != null ? !year.equals(edition.year) : edition.year != null) return false;
        if (sequenceName != null ? !sequenceName.equals(edition.sequenceName) : edition.sequenceName != null)
            return false;
        return sequenceNumber != null ? sequenceNumber.equals(edition.sequenceNumber) : edition.sequenceNumber == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (bookName != null ? bookName.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (sequenceName != null ? sequenceName.hashCode() : 0);
        result = 31 * result + (sequenceNumber != null ? sequenceNumber.hashCode() : 0);
        return result;
    }
}
