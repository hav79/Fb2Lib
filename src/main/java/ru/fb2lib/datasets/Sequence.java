package ru.fb2lib.datasets;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by hav on 25.01.16.
 */

@Entity
@Table(name = "sequences")
//uniqueConstraints = @UniqueConstraint(columnNames = {"name", "src_name"}))
public class Sequence extends BaseDataSet {

    private static final long serialVersionUID = -1551564898632211469L;

    @Column(name = "name")
    private String name;
    @Column(name = "src_name")
    private String srcName;

    @ManyToOne//(cascade = CascadeType.ALL)
    private Sequence parent;
    @OneToMany(mappedBy = "parent")
    private Set<Sequence> children;

    public Sequence() {
        super();
    }

    public Sequence(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public Sequence getParent() {
        return parent;
    }

    public void setParent(Sequence parent) {
        this.parent = parent;
    }

    public Set<Sequence> getChildren() {
        return children;
    }

    public void setChildren(Set<Sequence> children) {
        this.children = children;
    }

    public void addChild(Sequence sequence) {
        this.children.add(sequence);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sequence)) return false;
        if (!super.equals(o)) return false;

        Sequence sequence = (Sequence) o;

        if (name != null ? !name.equals(sequence.name) : sequence.name != null) return false;
        return srcName != null ? srcName.equals(sequence.srcName) : sequence.srcName == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (srcName != null ? srcName.hashCode() : 0);
        return result;
    }
}
