package ru.fb2lib.datasets;

import javax.persistence.Embeddable;

/**
 * Created by hav on 06.02.16.
 */

@Embeddable
public class ISBN {

    private String value;

    public ISBN() {
    }

    public ISBN(String value) {
        this.value = value.replaceAll("-", "").trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean isValidISBN(String value) {
        if (value.length() == 10)
            return isValidISBN10(value);
        else if (value.length() == 13)
            return isValidISBN13(value);

        return false;
    }

    private static boolean isValidISBN10(String value) {
        if (value.length() != 10)
            return false;

        int sum = 0, n;
        String s;

        for (int i=10; i>0; i--) {
            s = value.substring(10-i, 10-i+1);

            if (s.equals("X") || s.equals("x")) {
                n = 10;
            } else {
                try {
                    n = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            sum += i*n;
        }

        return (sum%11 == 0);
    }

    private static boolean isValidISBN13(String value) {
        int sum = 0, n, m;
        String s;

        for (int i=1; i<13; i++) {
            s = value.substring(i-1,i);
            try {
                n = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return false;
            }

            m = ((i%2 == 1) ? 1 : 3);
            sum += n*m;
        }

        try {
            n = Integer.parseInt(value.substring(12,13));
        } catch (NumberFormatException e) {
            return false;
        }

        return (((10-sum%10)%10)-n == 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISBN)) return false;

        ISBN isbn = (ISBN) o;

        return value != null ? value.equals(isbn.value) : isbn.value == null;

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
