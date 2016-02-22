import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.fb2lib.datasets.*;
import ru.fb2lib.parser.Fb2Parser;
import ru.fb2lib.parser.Parser;

import javax.xml.xpath.XPathExpressionException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hav on 02.02.16.
 */
public class Fb2ParserTest {
    String filename = "/home/hav/Проекты/IdeaProjects/Fb2Lib/testset/test.fb2";
    Parser parser;
//    Book book;

    @Before
    public void init() {
//        book = new Book(filename);
        parser = new Fb2Parser(filename);
    }

    @Test
    public void testParseAuthors() {
        try {
            List<Person> authors = parser.parseAuthors();
            Assert.assertEquals(2, authors.size());

            Assert.assertEquals("Роберт", authors.get(0).getFirstName());
            Assert.assertEquals("Линн", authors.get(0).getMiddleName());
            Assert.assertEquals("Асприн", authors.get(0).getLastName());
            Assert.assertEquals("Robert", authors.get(0).getSrcFirstName());
            Assert.assertEquals("Lynn", authors.get(0).getSrcMiddleName());
            Assert.assertEquals("Asprin", authors.get(0).getSrcLastName());

            Assert.assertEquals("Эрик", authors.get(1).getFirstName());
            Assert.assertEquals("", authors.get(1).getMiddleName());
            Assert.assertEquals("дель Карло", authors.get(1).getLastName());
            Assert.assertEquals("Eric", authors.get(1).getSrcFirstName());
            Assert.assertEquals("", authors.get(1).getSrcMiddleName());
            Assert.assertEquals("del Carlo", authors.get(1).getSrcLastName());
        } catch (XPathExpressionException e) {
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseTranslators() {
        try {
            List<Person> translators = parser.parseTranslators();
            Assert.assertEquals(1, translators.size());

            Assert.assertEquals("Алина", translators.get(0).getFirstName());
            Assert.assertEquals("Владимировна", translators.get(0).getMiddleName());
            Assert.assertEquals("Немирова", translators.get(0).getLastName());
        } catch (XPathExpressionException e) {
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseDocAuthors() {
        try {
            List<Person> docAuthors = parser.parseDocAuthors();
            Assert.assertEquals(1, docAuthors.size());

            Assert.assertEquals("a", docAuthors.get(0).getFirstName());
            Assert.assertEquals("a", docAuthors.get(0).getLastName());
        } catch (XPathExpressionException e) {
            Assert.fail("Exception: " + e.getMessage());
        }

    }

    @Test
    public void testParseTitle() {
        try {
            Assert.assertEquals("Варторн: Воскрешение", parser.parseTitle());
        } catch (XPathExpressionException e) {
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseSrcTitle() {
        try {
            Assert.assertEquals("Wartorn: Resurrection", parser.parseSrcTitle());
        } catch (XPathExpressionException e) {
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseYear() {
        try {
            Assert.assertEquals("2005", parser.parseYear());
        } catch (XPathExpressionException e) {
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseLang() {
        try {
            Assert.assertEquals(Language.ru, parser.parseLang());
        } catch (XPathExpressionException e) {
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseSrcLang() {
        try {
            Assert.assertEquals(Language.en, parser.parseSrcLang());
        } catch (XPathExpressionException e) {
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseGenres() {
        try {
            List<Genre> genres = parser.parseGenres();
            Assert.assertEquals(1, genres.size());

            Assert.assertEquals(Genre.sf_fantasy, genres.get(0));
        } catch (XPathExpressionException e) {
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseEdition() {
        try {
            Edition edition = parser.parseEdition();

            Assert.assertEquals("Варторн: Воскрешение", edition.getBookName());
            Assert.assertEquals("АСТ, АСТ Москва, Хранитель", edition.getPublisher());
            Assert.assertEquals("М.", edition.getCity());
            Assert.assertEquals("2006", edition.getYear());
            Assert.assertEquals("5-17-036598-5, 5-9713-3291-0, 5-9762-0355-8", edition.getIsbn());
            Assert.assertEquals("Век дракона 2", edition.getSequenceName());
        } catch (XPathExpressionException e) {
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    @Ignore
    @Test
    public void testParseCover() {

    }

    @Ignore
    @Test
    public void testParseSrcCover() {

    }

    @Test
    public void testParseHistory() {
        try {
            List<String> history = parser.parseDocumentInfo().getHistory();
            List<String> expectedHistory = new ArrayList<>();
            expectedHistory.add("v1.0 — создание fb2 Ego");
            expectedHistory.add("v 1.1 — дополнительное форматирование; добавлены аннотация, переводчик — (MCat78)");
            Assert.assertArrayEquals(expectedHistory.toArray(), history.toArray());
        } catch (XPathExpressionException e) {
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseDocumentInfo() {
        try {
            Document document = parser.parseDocumentInfo();

            Assert.assertEquals("ru.fb2lib.FictionBook Editor Release 2.6", document.getProgramUsed());

            DocDate docDate = document.getDocDate();
            Assert.assertEquals("2009-02-12", docDate.getStrValue());
            Assert.assertEquals(LocalDate.parse("2009-02-12"), docDate.getValue());

            Assert.assertEquals("http://www.litru.ru", document.getSrcUrl());
            Assert.assertEquals("Электронная Библиотека LITRU.RU", document.getSrcOcr());
            Assert.assertEquals("2B55B99A-EBAB-4A8E-A2EC-889A5E8B66E1", document.getDocumentId());
            Assert.assertEquals("1.0", document.getVersion());
        } catch (XPathExpressionException e) {
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseSequences() {
        try {
            List<BookSequences> sequences = parser.parseSequences();
            Assert.assertEquals(2, sequences.size());

            Assert.assertEquals("Варторн", sequences.get(0).getSequence().getName());
            Assert.assertEquals("Wartorn", sequences.get(0).getSequence().getSrcName());
            Assert.assertEquals(1, sequences.get(0).getNumber().longValue());

            Assert.assertEquals("Варторн 2", sequences.get(1).getSequence().getName());
            Assert.assertEquals("Wartorn 2", sequences.get(1).getSequence().getSrcName());
            Assert.assertEquals(1, sequences.get(1).getNumber().longValue());
            Assert.assertEquals(sequences.get(0).getSequence(), sequences.get(1).getSequence().getParent());
        } catch (XPathExpressionException e) {
            Assert.fail("Exception: " + e.getMessage());
        }

    }
}
