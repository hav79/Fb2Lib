package ru.fb2lib.datasets;

//import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hav on 05.01.16.
 */

@Entity
@Table(name = "persons")
public class Person extends BaseDataSet {

    private static final long serialVersionUID = -6551654891486564641L;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "src_first_name")
    private String srcFirstName;
    @Column(name = "src_middle_name")
    private String srcMiddleName;
    @Column(name = "src_last_name")
    private String srcLastName;

    @Column(name = "nickname")
    private String nickname;
    @Column(name = "email")
    private String email;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private Set<Book> booksWrited = new HashSet<>();

    @ManyToMany(mappedBy = "translators", cascade = CascadeType.ALL)
    private Set<Book> booksTranslated = new HashSet<>();

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private Set<Document> docsCreated = new HashSet<>();

    public Person() {
        super();
    }

    public Person(long id) {
        super(id);
        this.firstName = "";
        this.middleName = "";
        this.lastName = "";
    }

    public Person(String firstName, String middleName, String lastName) {
        super();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public Person(long id, String firstName, String middleName, String lastName) {
        this(firstName, middleName, lastName);
        setId(id);
    }

    public Person(String firstName, String middleName, String lastName,
                  String srcFirstName, String srcMiddleName, String srcLastName) {
        this(firstName, middleName, lastName);
        this.srcFirstName = srcFirstName;
        this.srcMiddleName = srcMiddleName;
        this.srcLastName = srcLastName;
    }

    @Override
    public String toString() {
        return getFullName() + " (" + getSrcFullName() + ")";
    }

    public String getFullName() {
        String res = getFirstName() + " ";
//            if (!middleName.isEmpty())
//                res += middleName + " ";
        return res + getLastName();
    }

    public String getSrcFullName() {
        String res = getSrcFirstName() + " ";
//            if (!middleName.isEmpty())
//                res += middleName + " ";
        return res + getSrcLastName();
    }

    public String getFirstName() {
        return firstName != null ? firstName : "";
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName != null ? middleName : "";
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName != null ? lastName : "";
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSrcFirstName() {
        return srcFirstName != null ? srcFirstName : "";
    }

    public void setSrcFirstName(String srcFirstName) {
        this.srcFirstName = srcFirstName;
    }

    public String getSrcMiddleName() {
        return srcMiddleName != null ? srcMiddleName : "";
    }

    public void setSrcMiddleName(String srcMiddleName) {
        this.srcMiddleName = srcMiddleName;
    }

    public String getSrcLastName() {
        return srcLastName != null ? srcLastName : "";
    }

    public void setSrcLastName(String srcLastName) {
        this.srcLastName = srcLastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Book> getBooksWrited() {
        return booksWrited;
    }

    public void setBooksWrited(Set<Book> booksWrited) {
        this.booksWrited = booksWrited;
    }

    public Set<Document> getDocsCreated() {
        return docsCreated;
    }

    public void setDocsCreated(Set<Document> docsCreated) {
        this.docsCreated = docsCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
//        if (!super.equals(o)) return false;

        Person person = (Person) o;

        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (middleName != null ? !middleName.equals(person.middleName) : person.middleName != null) return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;
        if (srcFirstName != null ? !srcFirstName.equals(person.srcFirstName) : person.srcFirstName != null)
            return false;
        if (srcMiddleName != null ? !srcMiddleName.equals(person.srcMiddleName) : person.srcMiddleName != null)
            return false;
        return srcLastName != null ? srcLastName.equals(person.srcLastName) : person.srcLastName == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (srcFirstName != null ? srcFirstName.hashCode() : 0);
        result = 31 * result + (srcMiddleName != null ? srcMiddleName.hashCode() : 0);
        result = 31 * result + (srcLastName != null ? srcLastName.hashCode() : 0);
        return result;
    }

}
