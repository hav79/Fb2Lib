package ru.fb2lib.datasets;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by hav on 06.01.16.
 */

@MappedSuperclass
public abstract class BaseDataSet implements Serializable {

    private static final long serialVersionUID = 0L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public BaseDataSet() {
        this.id = -1L;
    }

    public BaseDataSet(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseDataSet)) return false;

        BaseDataSet that = (BaseDataSet) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[ID=" + id + "]";
    }
}
