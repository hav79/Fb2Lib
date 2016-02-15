import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.fb2lib.parser.Fb2Parser;
import ru.fb2lib.parser.Parser;

import javax.xml.xpath.XPathExpressionException;

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
    public void testParseTitle() {
//        parser.parse();
        try {
            Assert.assertEquals("Варторн: Воскрешение", parser.parseTitle());
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

    }
}
