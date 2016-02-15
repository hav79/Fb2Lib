package ru.fb2lib.datasets;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hav on 06.01.16.
 */

@Entity
@Table(name = "books")
public class Book extends BaseDataSet {

    private static final long serialVersionUID = -1664655648751844476L;

    @Column(name = "filename")
    private String filename;

    @Column(name = "title")
    private String title;

    @Column(name = "src_title")
    private String srcTitle;

    @Column(name = "year")
    private String date;

    @Lob @Basic
    @Column(name = "annotation")
    private String annotation;

    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private Language lang;

    @Column(name = "src_lang")
    @Enumerated(EnumType.STRING)
    private Language srcLang;

    @Column(name = "genre")
    @ElementCollection//(fetch = FetchType.EAGER)
    private List<Genre> genres = new ArrayList<>();

    @Column(name = "keywords")
    @ElementCollection
    private List<String> keywords = new ArrayList<>();

    @OneToOne//(mappedBy = "book")
    private Cover cover;
    //TODO src-cover?
//    @OneToOne(mappedBy = "book")
//    private Cover srcCover;

    @OneToOne
    private Document document;

    @ManyToMany//(fetch = FetchType.EAGER)//(cascade = CascadeType.ALL)
    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"))
    private List<Person> authors = new ArrayList<>();

    @ManyToMany//(cascade = CascadeType.ALL)
    @JoinTable(name = "book_translators",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"))
    private List<Person> translators = new ArrayList<>();

    @ManyToOne//(cascade = CascadeType.ALL)
    private Edition edition;

    @Embedded
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "book_sequences")
    private List<BookSequences> sequences = new ArrayList<>();

    public Book() {
        super();
    }

    public Book(String filename) {
        super();
        this.filename = filename;
    }

    public Book(String filename, String title) {
        this(filename);
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrcTitle() {
        return srcTitle;
    }

    public void setSrcTitle(String srcTitle) {
        this.srcTitle = srcTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public List<Person> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Person> authors) {
        this.authors = authors;
    }

    public List<Person> getTranslators() {
        return translators;
    }

    public void setTranslators(List<Person> translators) {
        this.translators = translators;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    public Language getSrcLang() {
        return srcLang;
    }

    public void setSrcLang(Language srcLang) {
        this.srcLang = srcLang;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public List<BookSequences> getSequences() {
        return sequences;
    }

    public void setSequences(List<BookSequences> sequences) {
        this.sequences = sequences;
    }

    public void addSequence(Sequence sequence, Long number) {
        this.sequences.add(new BookSequences(sequence, number));
    }

    public void addAuthor(Person author) {
        this.authors.add(author);
    }

    public void addTranslator(Person translator) {
        this.translators.add(translator);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        if (!super.equals(o)) return false;

        Book book = (Book) o;

        if (!title.equals(book.title)) return false;
        if (srcTitle != null ? !srcTitle.equals(book.srcTitle) : book.srcTitle != null) return false;
        if (date != null ? !date.equals(book.date) : book.date != null) return false;
        return authors != null ? authors.equals(book.authors) : book.authors == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (srcTitle != null ? srcTitle.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        return result;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

//    public Cover getSrcCover() {
//        return srcCover;
//    }
//
//    public void setSrcCover(Cover srcCover) {
//        this.srcCover = srcCover;
//    }
}
