package ru.fb2lib.datasets;

import javax.persistence.Embeddable;
import java.time.LocalDate;

/**
 * Created by hav on 06.02.16.
 */

@Embeddable
public class DocDate {

    private String strValue;
    private LocalDate value;

    public DocDate() {
    }

    public DocDate(String strValue, LocalDate value) {

        this.strValue = strValue;
        this.value = value;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public LocalDate getValue() {
        return value;
    }

    public void setValue(LocalDate value) {
        this.value = value;
    }
}
