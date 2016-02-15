package ru.fb2lib.datasets;

import javafx.scene.image.Image;

import javax.persistence.*;

/**
 * Created by hav on 07.02.16.
 */

@Entity
@Table(name = "images")
public class Cover extends BaseDataSet {

    @Column(name = "name")
    private String name;

    @Lob @Basic
    @Column(name = "binary")
    private String binary;

    @Column(name = "content_type")
    private String contentType;

    @OneToOne(mappedBy = "cover")
    private Book book;

    public Cover() {
    }

    public Cover(String name, String binary, String contentType) {
        this.name = name;
        this.binary = binary;
        this.contentType = contentType;
    }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Image toImage() {
        return null;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cover)) return false;
        if (!super.equals(o)) return false;

        Cover cover = (Cover) o;

        if (binary != null ? !binary.equals(cover.binary) : cover.binary != null) return false;
        return book != null ? book.equals(cover.book) : cover.book == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (binary != null ? binary.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        return result;
    }
}
