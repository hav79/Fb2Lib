package ru.fb2lib.datasets;

import javax.persistence.*;

/**
 * Created by hav on 26.01.16.
 */

@Embeddable
public class BookSequences {

    private static final long serialVersionUID = -964897874516546648L;

    @ManyToOne//(cascade = CascadeType.ALL)
    private Sequence sequence;
    private Long number;

    public BookSequences() {
    }

    public BookSequences(Sequence sequence, Long number) {
        this.sequence = sequence;
        this.number = number;
//        this.book = book;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookSequences)) return false;
        if (!super.equals(o)) return false;

        BookSequences that = (BookSequences) o;

        return sequence != null ? sequence.equals(that.sequence) : that.sequence == null && (number != null ? number.equals(that.number) : that.number == null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sequence != null ? sequence.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }
}
