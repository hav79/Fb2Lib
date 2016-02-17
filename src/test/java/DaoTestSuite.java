import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.fb2lib.dao.BookDaoTest;
import ru.fb2lib.dao.EditionDaoTest;
import ru.fb2lib.dao.PersonDaoTest;

/**
 * Created by hav on 15.02.16.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BookDaoTest.class,
        PersonDaoTest.class,
        EditionDaoTest.class
})
public class DaoTestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
