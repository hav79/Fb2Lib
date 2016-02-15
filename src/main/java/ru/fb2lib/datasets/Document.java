package ru.fb2lib.datasets;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hav on 03.02.16.
 */

@Entity
@Table(name = "documents")
public class Document extends BaseDataSet {

    @Column(name = "doc_id")
    private String documentId;

    @Column(name = "program_used")
    private String programUsed;

    @Column(name = "version")
    private String version;
//    @Column(name = "date")
//    private String date;

    @Embedded
    private DocDate docDate;

    @Column(name = "src_ocr")
    private String srcOcr;

    @Column(name = "src_url")
    private String srcUrl;

    @Column(name = "history")
    @ElementCollection
    private List<String> history;

    @OneToOne(mappedBy = "document")
    private Book book;

    @ManyToMany
    @JoinTable(name = "doc_authors",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"))
    private List<Person> authors = new ArrayList<>();

    public Document() {
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getProgramUsed() {
        return programUsed;
    }

    public void setProgramUsed(String programUsed) {
        this.programUsed = programUsed;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }


    public DocDate getDocDate() {
        return docDate;
    }

    public void setDocDate(DocDate docDate) {
        this.docDate = docDate;
    }

    public String getSrcOcr() {
        return srcOcr;
    }

    public void setSrcOcr(String srcOcr) {
        this.srcOcr = srcOcr;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Person> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Person> authors) {
        this.authors = authors;
    }

    public void addAuthor(Person person) {
        this.authors.add(person);
    }
}
